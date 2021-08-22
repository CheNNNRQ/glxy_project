package com.diodi.eduService.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.diodi.eduService.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

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

    /**
     * 分页查询讲师
     * @return 分页的所有数据
     */
    Map<String, Object> getTeacherFrontList(Page<EduTeacher> teacherPage);
}
