package com.diodi.eduService.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.diodi.commonutils.R;
import com.diodi.eduService.entity.EduCourse;
import com.diodi.eduService.entity.EduTeacher;
import com.diodi.eduService.service.EduCourseService;
import com.diodi.eduService.service.EduTeacherService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 讲师前台功能
 * @author diodi
 * @create 2021-08-20-21:06
 */
@Api(tags = "讲师前台功能")
@RestController
@RequestMapping("eduService/teacherFront")
public class TeacherFrontController {
    @Autowired
    EduTeacherService teacherService;
    @Autowired
    EduCourseService courseService;

    /**
     * 分页查询讲师
     * @param page 当前页
     * @param limit 每页记录数
     * @return 分页的所有数据
     */
    @PostMapping("getTeacherFrontList/{page}/{limit}")
    public R getTeacherFrontList(@PathVariable Long page,
                                 @PathVariable Long limit) {
        Page<EduTeacher> teacherPage = new Page<>(page, limit);
        Map<String, Object> map = teacherService.getTeacherFrontList(teacherPage);
        //返回分页的所有数据
        return R.ok().data(map);
    }

    /**
     * 讲师详情查询
     * @param id 讲师id
     * @return 讲师基本信息和课程信息
     */
    @GetMapping("getTeacherFrontInfo/{id}")
    public R getTeacherFrontInfo(@PathVariable String id) {
        //讲师基本信息
        EduTeacher eduTeacher = teacherService.getById(id);
        //课程信息
        QueryWrapper<EduCourse> courseQueryWrapper = new QueryWrapper<>();
        courseQueryWrapper.eq("teacher_id", id);
        courseQueryWrapper.eq("status", "Normal");
        courseQueryWrapper.ne("is_deleted", true);
        List<EduCourse> eduCourseList = courseService.list(courseQueryWrapper);
        return R.ok().data("teacher",eduTeacher).data("courseList",eduCourseList);
    }
}
