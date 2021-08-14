package com.diodi.eduService.mapper;

import com.diodi.eduService.entity.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.diodi.eduService.entity.vo.CoursePublishVo;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author diodi
 * @since 2021-08-08
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {
    /**
     * <p>查询最终发布之前的课程信息 使用多个左连接查询</p>
     * <p>包含课程信息 课程父分类 课程子分类</p>
     * @param courseId 课程id
     * @return 包含信息的Vo类
     */
    public CoursePublishVo getPublishCourseInfo(String courseId);
}
