package com.diodi.eduService.controller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.diodi.commonutils.CourseWebVoOrder;
import com.diodi.commonutils.JwtUtils;
import com.diodi.commonutils.R;
import com.diodi.eduService.client.OrderClient;
import com.diodi.eduService.entity.EduCourse;
import com.diodi.eduService.entity.EduTeacher;
import com.diodi.eduService.entity.chapter.ChapterVo;
import com.diodi.eduService.entity.frontVo.CourseFrontVo;
import com.diodi.eduService.entity.frontVo.CourseWebVo;
import com.diodi.eduService.service.EduChapterService;
import com.diodi.eduService.service.EduCourseService;
import com.diodi.eduService.service.EduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 课程前台
 * @author diodi
 * @create 2021-08-21-13:47
 */
@Api(tags = "课程前台功能")
@RestController
@RequestMapping("eduService/courseFront")
public class CourseFrontController {
    @Autowired
    EduCourseService courseService;

    @Autowired
    EduChapterService chapterService;

    @Autowired
    EduTeacherService teacherService;
    @Autowired
    OrderClient orderClient;

    /**
     * 条件查询分页
     * @param page          页数
     * @param limit         每页记录数
     * @param courseFrontVo 前台查询的条件
     * @return 对应条件的课程map
     */
    @ApiOperation("条件查询分页")
    @PostMapping("getFrontCourseList/{page}/{limit}")
    public R getFrontCourseList(@PathVariable Long page,
                                @PathVariable Long limit,
                                @RequestBody(required = false) CourseFrontVo courseFrontVo) {
        Page<EduCourse> eduCoursePage = new Page<>(page, limit);
        Map<String, Object> map = courseService.getCourseFrontList(eduCoursePage, courseFrontVo);
        return R.ok().data(map);
    }

    /**
     * 根据课程id 编写sql语句查询课程信息 以及查询章节和小节
     * @param courseId 课程id
     * @return
     */
    @ApiOperation("根据课程id 编写sql语句查询课程信息 以及查询章节和小节")
    @GetMapping("getFrontCourseInfo/{courseId}")
    public R getFrontCourseInfo(@PathVariable String courseId,
                                HttpServletRequest request) {
        //根据课程id 编写sql语句查询课程信息
        CourseWebVo courseWebVo = courseService.getBaseCourseInfo(courseId);
        //根据课程id查询章节和小节
        List<ChapterVo> chapterVos = chapterService.nestedList(courseId);
        //todo 未登录情况优化
        String memberId;
        Boolean isBuyCourse;
        try {
            memberId = JwtUtils.getMemberIdByJwtToken(request);
            isBuyCourse = orderClient.isBuyCourse( memberId,courseId);
        } catch (Exception e) {
            return R.ok()
                    .data("courseWebVo", courseWebVo)
                    .data("chapterVideoList", chapterVos)
                    .data("isBuy", false);
        }
        return R.ok()
                .data("courseWebVo", courseWebVo)
                .data("chapterVideoList", chapterVos)
                .data("isBuy", isBuyCourse);
    }

    /**
     * 根据课程id查询课程信息
     * @param courseId
     * @return
     */
    @ApiOperation("根据课程id查询课程信息")
    @GetMapping("getCourseOrderInfo/{courseId}")
    public CourseWebVoOrder getCourseOrderInfo(@PathVariable String courseId) {
        EduCourse byId = courseService.getById(courseId);
        String teacherId = byId.getTeacherId();
        EduTeacher byId1 = teacherService.getById(teacherId);
        String teacherName = byId1.getName();
        CourseWebVoOrder courseWebVoOrder = new CourseWebVoOrder();
        BeanUtils.copyProperties(byId, courseWebVoOrder);
        courseWebVoOrder.setTeacherName(teacherName);
        return courseWebVoOrder;
    }
}
