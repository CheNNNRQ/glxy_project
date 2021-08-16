package com.diodi.eduService.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.diodi.eduService.entity.EduTeacher;
import com.diodi.eduService.mapper.EduTeacherMapper;
import com.diodi.eduService.service.EduTeacherService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author diodi
 * @since 2021-08-02
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {
    /**
     * 查询名师
     * @return
     */
    @Override
    @Cacheable(value = "index" , key = "'teacherList'")
    public List<EduTeacher> getIndexTeacherList() {
        QueryWrapper<EduTeacher> eduTeacherQueryWrapper = new QueryWrapper<>();
        eduTeacherQueryWrapper.orderByDesc("sort");
        eduTeacherQueryWrapper.last("limit 4");
        List<EduTeacher> eduTeachers = baseMapper.selectList(eduTeacherQueryWrapper);
        return eduTeachers;
    }
}
