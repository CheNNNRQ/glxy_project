package com.diodi.eduService.service;

import com.diodi.eduService.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author diodi
 * @since 2021-08-05
 */
public interface EduSubjectService extends IService<EduSubject> {

    void saveSubject(MultipartFile file,
                     EduSubjectService eduSubjectService);
}
