package com.diodi.eduService.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.diodi.eduService.entity.EduSubject;
import com.diodi.eduService.entity.excel.SubjectData;
import com.diodi.eduService.listener.SubjectExcelListener;
import com.diodi.eduService.mapper.EduSubjectMapper;
import com.diodi.eduService.service.EduSubjectService;
import com.diodi.servicebase.exceptionhandler.GuliException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author diodi
 * @since 2021-08-05
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    @Override
    public void saveSubject(MultipartFile file,
                            EduSubjectService eduSubjectService) {
        try {
            //文件输入流
            InputStream is = file.getInputStream();

            //调用方法进行读取
            EasyExcel.read(is, SubjectData.class, new SubjectExcelListener(eduSubjectService))
                     .sheet().doRead();

        }catch (Exception e){
            e.printStackTrace();
            throw new GuliException(20003,"添加失败service");
        }
    }
}
