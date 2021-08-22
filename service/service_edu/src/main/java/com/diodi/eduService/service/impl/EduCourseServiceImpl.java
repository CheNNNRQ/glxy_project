package com.diodi.eduService.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.diodi.eduService.entity.EduCourse;
import com.diodi.eduService.entity.EduCourseDescription;
import com.diodi.eduService.entity.frontVo.CourseFrontVo;
import com.diodi.eduService.entity.frontVo.CourseWebVo;
import com.diodi.eduService.entity.vo.CourseInfoVo;
import com.diodi.eduService.entity.vo.CoursePublishVo;
import com.diodi.eduService.mapper.EduCourseMapper;
import com.diodi.eduService.service.EduChapterService;
import com.diodi.eduService.service.EduCourseDescriptionService;
import com.diodi.eduService.service.EduCourseService;
import com.diodi.eduService.service.EduVideoService;
import com.diodi.servicebase.exceptionhandler.GuliException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 * @author diodi
 * @since 2021-08-08
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {
    @Autowired
    EduCourseDescriptionService eduCourseDescriptionService;
    @Autowired
    EduVideoService videoService;
    @Autowired
    EduChapterService chapterService;

    /**
     * 添加课程基本信息
     * @param courseInfoVo 表单提交数据封装类
     */
    @Override
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {
        //1 向课程表添加课程基本信息
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo, eduCourse);
        //insert需要EduCourse 把CourseInfoVo转成EduCourse
        int insert = baseMapper.insert(eduCourse);
        if (insert == 0) {
            throw new GuliException(20002, "添加课程失败");
        }
        //课程信息和详细信息应该对应 获取添加之后的课程id
        String id = eduCourse.getId();
        //2 向课程简介表添加课程简介
        //需要向另一张表添加数据 自动注入eduCourseDescriptionService类 调用它的方法
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
//        BeanUtils.copyProperties(courseInfoVo,eduCourseDescription);
        eduCourseDescription.setId(id);
        eduCourseDescription.setDescription(courseInfoVo.getDescription());
        eduCourseDescriptionService.save(eduCourseDescription);
        return id;
    }

    /**
     * 根据课程id查询课程所有信息
     * @param courseId 课程id
     * @return 课程所有信息 CourseInfoVo
     */
    @Override
    public CourseInfoVo getCourseInfo(String courseId) {
        //创建最后返回的对象
        CourseInfoVo courseInfoVo = new CourseInfoVo();
        //查询课程表
        EduCourse eduCourse = baseMapper.selectById(courseId);
        BeanUtils.copyProperties(eduCourse, courseInfoVo);
        //查询课程描述表
        EduCourseDescription courseDescription = eduCourseDescriptionService.getById(courseId);
        //将课程描述信息设置到courseInfoVo中
        if (courseDescription != null) {
            courseInfoVo.setDescription(courseDescription.getDescription());
        }
        return courseInfoVo;
    }

    /**
     * 修改课程信息
     * @param courseInfoVo 前端传来的课程信息
     * @return
     */
    @Override
    public void updateCourseInfo(CourseInfoVo courseInfoVo) {
        //修改课程表
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo, eduCourse);
        int i = baseMapper.updateById(eduCourse);
        //判断是否有影响的行数
        if (i == 0) {
            throw new GuliException(20001, "修改课程信息失败");
        }
        //修改描述表
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setId(courseInfoVo.getId());
        eduCourseDescription.setDescription(courseInfoVo.getDescription());
        eduCourseDescriptionService.updateById(eduCourseDescription);
    }

    /**
     * 根据课程id查询课程确认信息
     * @param courseId 课程id
     * @return 包含最终信息的Vo类
     */
    @Override
    public CoursePublishVo getPublishCourseInfo(String courseId) {
        return baseMapper.getPublishCourseInfo(courseId);
    }

    /**
     * 删除课程的所有信息
     * <p>把视频小节章节描述课程本身都删除</p>
     * @param courseId 课程ID
     */
    @Override
    public void removeCourse(String courseId) {
        //删除小节
        videoService.removeVideoByCourseId(courseId);
        //删除章节
        chapterService.removeChapterByCourseId(courseId);
        //删除简介
        eduCourseDescriptionService.removeById(courseId);
        //删除课程
        int i = baseMapper.deleteById(courseId);
        if (i < 1) {
            throw new GuliException(20001, "删除失败");
        }
    }

    /**
     * 查询学习人数最多的8个课程
     */
    @Override
    @Cacheable(value = "index" , key = "'courseList'")
    public List<EduCourse> getIndexCourseList() {
        QueryWrapper<EduCourse> eduCourseQueryWrapper = new QueryWrapper<>();
        eduCourseQueryWrapper.orderByDesc("view_count");
        eduCourseQueryWrapper.last("limit 8");
        List<EduCourse> eduCourses = baseMapper.selectList(eduCourseQueryWrapper);
        return eduCourses;
    }

    /**
     * 条件查询分页
     * @param eduCoursePage 分页对象
     * @param courseFrontVo 前台查询的条件
     * @return 对应条件的课程map
     */
    @Override
    public Map<String, Object> getCourseFrontList(Page<EduCourse> eduCoursePage,
                                                  CourseFrontVo courseFrontVo) {
        QueryWrapper<EduCourse> courseQueryWrapper = new QueryWrapper<>();
        //课程父id
        String subjectParentId = courseFrontVo.getSubjectParentId();
        //课程子id
        String subjectId = courseFrontVo.getSubjectId();
        //创建时间
        String gmtCreateSort = courseFrontVo.getGmtCreateSort();
        //价格
        String priceSort = courseFrontVo.getPriceSort();
        //关注度
        String countSort = courseFrontVo.getBuyCountSort();
        //判断条件是否为空 不为空就查询
        if (!StringUtils.isEmpty(subjectParentId)){
            courseQueryWrapper.eq("subject_parent_id", subjectParentId);
        }
        if (!StringUtils.isEmpty(subjectId)){
            courseQueryWrapper.eq("subject_id", subjectId);
        }
        if (!StringUtils.isEmpty(gmtCreateSort)){
            courseQueryWrapper.orderByDesc("gmt_create",gmtCreateSort );
        }
        if (!StringUtils.isEmpty(priceSort)){
            courseQueryWrapper.orderByDesc("price", priceSort);
        }
        if (!StringUtils.isEmpty(countSort)){
            courseQueryWrapper.orderByDesc("buy_count", countSort);
        }
        //将条件查询出的结果放到page里
        baseMapper.selectPage(eduCoursePage, courseQueryWrapper);
        //总记录数
        long total = eduCoursePage.getTotal();
        //数据集合
        List<EduCourse> courseList = eduCoursePage.getRecords();
        //每页记录数
        long size = eduCoursePage.getSize();
        //当前页
        long current = eduCoursePage.getCurrent();
        //总页数
        long pages = eduCoursePage.getPages();
        //是否有上一页
        boolean hasPrevious = eduCoursePage.hasPrevious();
        //是否有下一页
        boolean hasNext = eduCoursePage.hasNext();

        HashMap<String, Object> map = new HashMap<>();
        map.put("total",total);
        map.put("list",courseList);
        map.put("size",size);
        map.put("current",current);
        map.put("pages",pages);
        map.put("hasPrevious",hasPrevious);
        map.put("hasNext",hasNext);
        return map;
    }

    /**
     * 根据课程id 编写sql语句查询课程信息
     * @param courseId
     * @return
     */
    @Override
    public CourseWebVo getBaseCourseInfo(String courseId) {
        return baseMapper.getBaseCourseInfo(courseId);
    }
}

