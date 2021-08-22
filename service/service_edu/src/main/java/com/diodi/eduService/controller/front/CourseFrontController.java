package com.diodi.eduService.controller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.diodi.commonutils.R;
import com.diodi.eduService.entity.EduCourse;
import com.diodi.eduService.entity.chapter.ChapterVo;
import com.diodi.eduService.entity.frontVo.CourseFrontVo;
import com.diodi.eduService.entity.frontVo.CourseWebVo;
import com.diodi.eduService.service.EduChapterService;
import com.diodi.eduService.service.EduCourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 课程前台
 * @author diodi
 * @create 2021-08-21-13:47
 */
@Api(tags = "课程前台功能")
@RestController
@CrossOrigin
@RequestMapping("eduService/courseFront")
public class CourseFrontController {
    @Autowired
    EduCourseService courseService;

    @Autowired
    EduChapterService chapterService;
    /**
     * 条件查询分页
     * @param page 页数
     * @param limit 每页记录数
     * @param courseFrontVo 前台查询的条件
     * @return 对应条件的课程map
     */
    @ApiOperation("条件查询分页")
    @PostMapping("getFrontCourseList/{page}/{limit}")
    public R getFrontCourseList(@PathVariable Long page,
                                @PathVariable Long limit,
                                @RequestBody(required = false) CourseFrontVo courseFrontVo){
        Page<EduCourse> eduCoursePage = new Page<>(page, limit);
        Map<String, Object> map = courseService.getCourseFrontList(eduCoursePage, courseFrontVo);
        return R.ok().data( map);
    }

    /**
     * 根据课程id 编写sql语句查询课程信息 以及查询章节和小节
     * @param courseId 课程id
     * @return
     */
    @ApiOperation("根据课程id 编写sql语句查询课程信息 以及查询章节和小节")
    @GetMapping("getFrontCourseInfo/{courseId}")
    public R getFrontCourseInfo(@PathVariable String courseId){
        //根据课程id 编写sql语句查询课程信息
        CourseWebVo courseWebVo = courseService.getBaseCourseInfo(courseId);
        //根据课程id查询章节和小节
        List<ChapterVo> chapterVos = chapterService.nestedList(courseId);
        return R.ok().data("courseWebVo",courseWebVo).data("chapterVideoList",chapterVos);
    }
}
