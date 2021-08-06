package com.diodi.eduService.controller;

import com.diodi.commonutils.R;
import com.diodi.eduService.service.EduSubjectService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
@CrossOrigin //解决跨域问题
@Api(description="课程分类管理")
public class EduSubjectController {
    @Autowired
    private EduSubjectService eduSubjectService;

    //添加课程分类
    //获取上传过来的文件，把文件内容读取出来
    @PostMapping("/addSubject")
    public R addSubject(MultipartFile file){
        //获取上传的excel文件 MultipartFile

        eduSubjectService.saveSubject(file,eduSubjectService);
        //判断返回集合是否为空

        return R.ok();
    }
}

