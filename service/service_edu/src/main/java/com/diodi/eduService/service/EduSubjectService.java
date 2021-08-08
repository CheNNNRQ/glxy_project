package com.diodi.eduService.service;

import com.diodi.eduService.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.diodi.eduService.entity.subject.OneSubject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author diodi
 * @since 2021-08-05
 */
public interface EduSubjectService extends IService<EduSubject> {

    /**
     * 添加课程分类 获取上传过来的文件，把文件内容读取出来
     * @param file 前段传过来的文件
     * @return
     */
    void saveSubject(MultipartFile file,
                     EduSubjectService eduSubjectService);

    /**
     * 获取课程分类列表
     * @return 返回的一级分类列表 其中包含二级分类
     */
    List<OneSubject> getAllOneTwoSubject();
}
