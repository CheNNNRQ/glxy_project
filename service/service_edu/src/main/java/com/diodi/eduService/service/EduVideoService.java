package com.diodi.eduService.service;

import com.diodi.eduService.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author diodi
 * @since 2021-08-08
 */
public interface EduVideoService extends IService<EduVideo> {

    /**
     * 根据课程id删除小节
     * @param courseId 课程id
     */
    void removeVideoByCourseId(String courseId);
}
