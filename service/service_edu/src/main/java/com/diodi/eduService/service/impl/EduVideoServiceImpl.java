package com.diodi.eduService.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.diodi.eduService.entity.EduVideo;
import com.diodi.eduService.mapper.EduVideoMapper;
import com.diodi.eduService.service.EduVideoService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author diodi
 * @since 2021-08-08
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {

    //TODO 删除小节时删除对应视频文件
    /**
     * 根据课程id删除小节
     * @param courseId 课程id
     */
    @Override
    public void removeVideoByCourseId(String courseId) {
        QueryWrapper<EduVideo> eduVideoQueryWrapper = new QueryWrapper<>();
        eduVideoQueryWrapper.eq("course_id", courseId);
        baseMapper.delete(eduVideoQueryWrapper);
    }
}
