package com.diodi.eduService.service;

import com.diodi.eduService.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author diodi
 * @since 2021-08-02
 */
public interface EduTeacherService extends IService<EduTeacher> {

    /**
     * 查询名师
     * @return
     */
    List<EduTeacher> getIndexTeacherList();
}
