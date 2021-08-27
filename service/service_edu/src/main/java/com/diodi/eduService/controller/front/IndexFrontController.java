package com.diodi.eduService.controller.front;

import com.diodi.commonutils.R;
import com.diodi.eduService.entity.EduCourse;
import com.diodi.eduService.entity.EduTeacher;
import com.diodi.eduService.service.EduCourseService;
import com.diodi.eduService.service.EduTeacherService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 用户前端首页数据
 * <p>查询热门课程和名师的接口</p>
 * @author diodi
 * @create 2021-08-16-11:40
 */
@Api(tags = "查询热门课程和名师的接口")
@RestController
@RequestMapping("eduService/indexFront")
public class IndexFrontController {
    @Autowired
    EduCourseService courseService;
    @Autowired
    EduTeacherService teacherService;


    @GetMapping("index")
    public R index() {
        //查询学习人数最多的8个课程
        List<EduCourse> eduCourses = courseService.getIndexCourseList();
        //查询名师
        List<EduTeacher> eduTeachers = teacherService.getIndexTeacherList();
        return R.ok().data("eduCourses", eduCourses).data("eduTeachers", eduTeachers);
    }
}
