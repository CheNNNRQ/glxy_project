package com.diodi.eduService.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.diodi.eduService.entity.EduCourse;
import com.diodi.eduService.entity.frontVo.CourseFrontVo;
import com.diodi.eduService.entity.frontVo.CourseWebVo;
import com.diodi.eduService.entity.vo.CourseInfoVo;
import com.diodi.eduService.entity.vo.CoursePublishVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author diodi
 * @since 2021-08-08
 */
public interface EduCourseService extends IService<EduCourse> {

    /**
     * 添加课程基本信息
     * @param courseInfoVo 表单提交数据封装类
     */
    String saveCourseInfo(CourseInfoVo courseInfoVo);

    /**
     * 根据课程id查询课程所有信息
     * @param courseId 课程id
     * @return 课程所有信息 CourseInfoVo
     */
    CourseInfoVo getCourseInfo(String courseId);

    /**
     * 修改课程信息
     * @param courseInfoVo 前端传来的课程信息
     * @return
     */
    void updateCourseInfo(CourseInfoVo courseInfoVo);

    /**
     * 根据课程id查询课程确认信息
     * @param courseId 课程id
     * @return 包含最终信息的Vo类
     */
    CoursePublishVo getPublishCourseInfo(String courseId);

    /**
     * 删除课程的所有信息
     * <p>把视频小节章节描述课程本身都删除</p>
     * @param courseId 课程ID
     */
    void removeCourse(String courseId);

    /**
     * 查询学习人数最多的8个课程
     * @return
     */
    List<EduCourse> getIndexCourseList();

    /**
     * 条件查询分页
     * @param eduCoursePage 分页对象
     * @param courseFrontVo 前台查询的条件
     * @return 对应条件的课程map
     */
    Map<String, Object> getCourseFrontList(Page<EduCourse> eduCoursePage,
                                           CourseFrontVo courseFrontVo);

    /**
     * 根据课程id 编写sql语句查询课程信息
     * @param courseId
     * @return
     */
    CourseWebVo getBaseCourseInfo(String courseId);
}
