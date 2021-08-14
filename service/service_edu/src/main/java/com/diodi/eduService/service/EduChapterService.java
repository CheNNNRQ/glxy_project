package com.diodi.eduService.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.diodi.eduService.entity.EduChapter;
import com.diodi.eduService.entity.chapter.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author diodi
 * @since 2021-08-08
 */
public interface EduChapterService extends IService<EduChapter> {

    /**
     * 嵌套章节数据列表
     * @param courseId 课程id
     * @return 课程下的章节列表
     */
    List<ChapterVo> nestedList(String courseId);

    /**
     * 删除章节 这里采用的删除方式是如果章节下有小节 不让删除 反之可以删除
     * @param chapterId 章节id
     * @return
     */
    Boolean deleteChapter(String chapterId);

    /**
     * 根据课程id删除对应章节
     * @param courseId
     */
    void removeChapterByCourseId(String courseId);
}
