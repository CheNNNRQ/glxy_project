package com.diodi.eduService.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.diodi.commonutils.R;
import com.diodi.eduService.entity.EduCourse;
import com.diodi.eduService.entity.vo.CourseInfoVo;
import com.diodi.eduService.entity.vo.CoursePublishVo;
import com.diodi.eduService.entity.vo.CourseQuery;
import com.diodi.eduService.service.EduCourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 * @author diodi
 * @since 2021-08-08
 */
@Api(tags = "课程管理")
@RestController
@RequestMapping("/eduService/course")
@CrossOrigin
public class EduCourseController {

    @Autowired
    EduCourseService eduCourseService;

    /**
     * 添加课程基本信息
     * @param courseInfoVo 表单提交数据封装类
     * @return
     */
    @ApiOperation("添加课程基本信息")
    @PostMapping("addCourseInfo")
    public R addCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {
        String id = eduCourseService.saveCourseInfo(courseInfoVo);
        return R.ok().data("courseId", id);

    }

    /**
     * 根据课程id查询课程所有信息
     * @param courseId 课程id
     * @return 课程所有信息 CourseInfoVo
     */
    @ApiOperation("根据课程id查询课程所有信息")
    @GetMapping("getCourseInfo/{courseId}")
    public R getCourseInfo(@PathVariable String courseId) {
        CourseInfoVo courseInfoVo = eduCourseService.getCourseInfo(courseId);
        return R.ok().data("courseInfoVo", courseInfoVo);
    }

    /**
     * 修改课程信息
     * @param courseInfoVo 前端传来的课程信息
     * @return
     */
    @ApiOperation("修改课程信息")
    @PostMapping("updateCourseInfo")
    public R updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {
        eduCourseService.updateCourseInfo(courseInfoVo);
        return R.ok();
    }

    /**
     * 根据课程id查询课程确认信息
     * @param courseId 课程id
     * @return 包含最终信息的Vo类
     */
    @ApiOperation("根据课程id查询课程确认信息")
    @GetMapping("getPublishCourseInfo/{courseId}")
    public R getPublishCourseInfo(@PathVariable String courseId) {
        CoursePublishVo coursePublishVo = eduCourseService.getPublishCourseInfo(courseId);
        return R.ok().data("CoursePublishVo", coursePublishVo);
    }

    /**
     * 课程最终发布 修改发布状态
     * @param id id
     * @return
     */
    @ApiOperation("课程最终发布 修改发布状态")
    @PostMapping("publishCourse/{id}")
    public R publishCourse(@PathVariable String id) {
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(id);
        eduCourse.setStatus("Normal");
        boolean flag = eduCourseService.updateById(eduCourse);
        if (flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    /**
     * 所有课程列表
     * @return 课程列表
     */
    @ApiOperation("所有讲师列表")
    @GetMapping("getCourseList")
    public R getCourseList() {
        List<EduCourse> list = eduCourseService.list(null);
        return R.ok().data("list", list);
    }

    /**
     * 条件查询带分页
     * @param page        当前页
     * @param limit       每页显示记录数
     * @param courseQuery 查询类
     * @return
     */
    @ApiOperation("多条件查询课程带分页")
    @PostMapping("pageCourseCondition/{page}/{limit}")
    public R pageCourseCondition(@PathVariable Long page,
                                 @PathVariable Long limit,
                                 @RequestBody(required = false) CourseQuery courseQuery) {
        Page<EduCourse> page1 = new Page<>(page, limit);
        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();
        String title = courseQuery.getTitle();
        String status = courseQuery.getStatus();
        if (!StringUtils.isEmpty(title)) {
            queryWrapper.like("title",title);
        }
        if (!StringUtils.isEmpty(status)) {
            queryWrapper.eq("status",status);
        }
        queryWrapper.orderByDesc("gmt_create");
        eduCourseService.page(page1,queryWrapper);
        long total = page1.getTotal();
        List<EduCourse> records = page1.getRecords();
        return R.ok().data("total",total).data("rows", records);
    }

    /**
     * 删除课程的所有信息
     * <p>把视频小节章节描述课程本身都删除</p>
     * @param courseId 课程ID
     * @return
     */
    @ApiOperation("删除课程的所有信息")
    @DeleteMapping("removeCourseAll/{courseId}")
    public R removeCourseAll(@PathVariable String courseId) {
        eduCourseService.removeCourse(courseId);
        return R.ok();
    }
}

