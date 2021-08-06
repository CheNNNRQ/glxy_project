package com.diodi.eduService.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.diodi.commonutils.R;
import com.diodi.eduService.entity.EduTeacher;
import com.diodi.eduService.entity.vo.TeacherQuery;
import com.diodi.eduService.service.EduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 * @author diodi
 * @since 2021-08-02
 */
@Api(description = "讲师管理")
@RestController
@CrossOrigin
@RequestMapping("/eduService/teacher")
public class EduTeacherController {
    @Autowired
    private EduTeacherService teacherService;

    /**
     * 所有讲师列表
     * @return
     */
    @ApiOperation("所有讲师列表")
    @GetMapping("findAll")
    public R list() {
        List<EduTeacher> list = teacherService.list(null);
        return R.ok().data("items", list);
    }

    /**
     * 逻辑删除ById
     * @param id
     * @return
     */
    @ApiOperation("逻辑删除ById")
    @DeleteMapping("delete/{id}")
    public R removeById(@PathVariable String id) {
        boolean flag = teacherService.removeById(id);
        if (flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    /**
     * 分页查询
     * @param current 当前页
     * @param limit   每页显示记录数
     * @return
     */
    @ApiOperation("分页讲师列表")
    @GetMapping("pageTeacher/{current}/{limit}")
    public R pageListTeacher(@PathVariable Long current,
                             @PathVariable Long limit) {
        Page<EduTeacher> page = new Page<>(current, limit);
        //分页查询，查完后，会将数据封装在page中
        teacherService.page(page, null);
        //获取总记录数
        long total = page.getTotal();
        //获取查询到的数据
        List<EduTeacher> records = page.getRecords();
        return R.ok().data("total", total).data("records", records);
    }

    /**
     * 条件查询带分页
     * @param current      当前页
     * @param limit        每页显示记录数
     * @param teacherQuery 查询类
     * @return
     */
    @ApiOperation("条件查询带分页")
    @PostMapping("pageTeacherCondition/{current}/{limit}")
    public R pageTeacherCondition(@PathVariable long current,
                                  @PathVariable long limit,
                                  @RequestBody(required = false) TeacherQuery teacherQuery) {
        //创建分页page对象
        Page<EduTeacher> page = new Page<>(current, limit);
        //构建条件对象
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        //多条件组合查询
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();
        if (!StringUtils.isEmpty(name)) {
            wrapper.like("name", name);
        }
        if (!StringUtils.isEmpty(level)) {
            wrapper.eq("level", level);
        }
        if (!StringUtils.isEmpty(begin)) {
            wrapper.ge("gmt_create", begin);
        }
        if (!StringUtils.isEmpty(end)) {
            wrapper.le("gmt_modified", end);
        }
        wrapper.orderByDesc("gmt_create");
        //调用方法实现多条件分页查询
        teacherService.page(page, wrapper);
        //获取总记录数
        long total = page.getTotal();
        //获取查询到的数据
        List<EduTeacher> records = page.getRecords();
        return R.ok().data("total", total).data("rows", records);
    }

    /**
     * 添加教师
     * @param eduTeacher 前端传过来的教师对象
     * @return
     */
    @ApiOperation("添加教师")
    @PostMapping("addTeacher")
    public R addTeacher(@RequestBody EduTeacher eduTeacher) {
        boolean save = teacherService.save(eduTeacher);
        if (save) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    /**
     * 根据id查询,用于信息回显
     * @param id 前端传过来的id
     * @return
     */
    @ApiOperation("根据id查询教师")
    @GetMapping("getById/{id}")
    public R getById(@PathVariable String id) {
        return R.ok().data("item", teacherService.getById(id));
    }

    /**
     * 修改教师
     * @param eduTeacher 前端传过来的教师对象
     * @return
     */
    @ApiOperation("修改教师")
    @PostMapping("updateById")
    public R updateById(@RequestBody EduTeacher eduTeacher) {
        boolean b = teacherService.updateById(eduTeacher);
        if (b) {
            return R.ok();
        } else {
            return R.error();
        }
    }
}

