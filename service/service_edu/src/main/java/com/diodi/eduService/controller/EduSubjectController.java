package com.diodi.eduService.controller;

import com.diodi.commonutils.R;
import com.diodi.eduService.entity.subject.OneSubject;
import com.diodi.eduService.service.EduSubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author diodi
 * @since 2021-08-05
 */
@RestController
@RequestMapping("/eduService/subject")
@Api(tags ="课程分类管理")
public class EduSubjectController {
    @Autowired
    private EduSubjectService eduSubjectService;

    /**
     * 添加课程分类 获取上传过来的文件，把文件内容读取出来
     * @param file 前段传过来的文件
     * @return
     */
    @PostMapping("addSubject")
    public R addSubject(MultipartFile file){
        //获取上传的excel文件 MultipartFile

        eduSubjectService.saveSubject(file,eduSubjectService);
        //判断返回集合是否为空

        return R.ok();
    }
    /**
     * 获取课程分类列表
     * @return 返回的一级分类列表 其中包含二级分类
     */
    @ApiOperation("获取课程分类列表")
    @GetMapping("getAllSubject")
    public R getAllSubject(){
        List<OneSubject> list = eduSubjectService.getAllOneTwoSubject();
        return R.ok().data("list",list);
    }
}

