package com.diodi.eduService.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.diodi.eduService.entity.EduChapter;
import com.diodi.eduService.entity.EduVideo;
import com.diodi.eduService.entity.chapter.ChapterVo;
import com.diodi.eduService.entity.chapter.VideoVo;
import com.diodi.eduService.mapper.EduChapterMapper;
import com.diodi.eduService.service.EduChapterService;
import com.diodi.eduService.service.EduVideoService;
import com.diodi.servicebase.exceptionhandler.GuliException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 * @author diodi
 * @since 2021-08-08
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    EduVideoService eduVideoService;

    /**
     * 嵌套章节数据列表
     * @param courseId 课程id
     * @return 课程下的章节列表
     */
    @Override
    public List<ChapterVo> nestedList(String courseId) {
        //创建最终返回的list对象
        List<ChapterVo> finalReturn = new ArrayList<>();
        //查询章节信息
        QueryWrapper<EduChapter> eduChapterQueryWrapper = new QueryWrapper<>();
        eduChapterQueryWrapper.eq("course_id", courseId);
        eduChapterQueryWrapper.orderByAsc("id");
        List<EduChapter> chapterList = baseMapper.selectList(eduChapterQueryWrapper);
        //查询章节下的小节信息
        QueryWrapper<EduVideo> eduVideoQueryWrapper = new QueryWrapper<>();
        eduVideoQueryWrapper.eq("course_id", courseId);
        eduVideoQueryWrapper.orderByAsc("id");
        List<EduVideo> videoList = eduVideoService.list(eduVideoQueryWrapper);
        //封装章节信息
        for (EduChapter eduChapter : chapterList) {
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(eduChapter, chapterVo);
            //封装小节信息
            List<VideoVo> videoVos = new ArrayList<>();
            for (EduVideo eduVideo : videoList) {
                String eduVideoChapterId = eduVideo.getChapterId();
                if (eduVideoChapterId.equals(chapterVo.getId())) {
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(eduVideo, videoVo);
                    videoVos.add(videoVo);
                }
            }
            chapterVo.setChildren(videoVos);
            finalReturn.add(chapterVo);
        }

        //返回最终数据
        return finalReturn;
    }

    /**
     * 删除章节 这里采用的删除方式是如果章节下有小节 不让删除 反之可以删除
     * @param chapterId 章节id
     * @return
     */
    @Override
    public Boolean deleteChapter(String chapterId) {
        QueryWrapper<EduVideo> eduVideoQueryWrapper = new QueryWrapper<>();
        eduVideoQueryWrapper.eq("chapter_id", chapterId);
        int count = eduVideoService.count(eduVideoQueryWrapper);
        if (count == 0) {
            int i = baseMapper.deleteById(chapterId);
            return i>0;
        } else {
            throw new GuliException(20001, "还有小节没删除");
        }
    }

    /**
     * 根据课程id删除对应章节
     * @param courseId
     */
    @Override
    public void removeChapterByCourseId(String courseId) {
        QueryWrapper<EduChapter> eduChapterQueryWrapper = new QueryWrapper<>();
        eduChapterQueryWrapper.eq("course_id", courseId);
        baseMapper.delete(eduChapterQueryWrapper);
    }
}
