package com.diodi.eduService.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.diodi.eduService.entity.EduSubject;
import com.diodi.eduService.entity.excel.SubjectData;
import com.diodi.eduService.entity.subject.OneSubject;
import com.diodi.eduService.entity.subject.TwoSubject;
import com.diodi.eduService.listener.SubjectExcelListener;
import com.diodi.eduService.mapper.EduSubjectMapper;
import com.diodi.eduService.service.EduSubjectService;
import com.diodi.servicebase.exceptionhandler.GuliException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 * @author diodi
 * @since 2021-08-05
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    /**
     * 添加课程分类 获取上传过来的文件，把文件内容读取出来
     * @param file 前段传过来的文件
     * @return
     */
    @Override
    public void saveSubject(MultipartFile file,
                            EduSubjectService eduSubjectService) {
        try {
            //文件输入流
            InputStream is = file.getInputStream();

            //调用方法进行读取
            EasyExcel.read(is, SubjectData.class, new SubjectExcelListener(eduSubjectService))
                     .sheet().doRead();

        } catch (Exception e) {
            e.printStackTrace();
            throw new GuliException(20003, "添加失败service");
        }
    }

    /**
     * 获取课程分类列表
     * @return 返回的一级分类列表 其中包含二级分类
     */
    @Override
    public List<OneSubject> getAllOneTwoSubject() {
        //查询所有一级分类
        QueryWrapper<EduSubject> wrapperOne = new QueryWrapper<>();
        wrapperOne.eq("parent_id", "0");
        List<EduSubject> eduSubjectsOne = baseMapper.selectList(wrapperOne);
        //查询所有二级分类
        QueryWrapper<EduSubject> wrapperTwo = new QueryWrapper<>();
        wrapperTwo.ne("parent_id", "0");
        List<EduSubject> eduSubjectsTwo = baseMapper.selectList(wrapperTwo);
        //创建list 返回封装的最终数据
        ArrayList<OneSubject> finalList = new ArrayList<>();
        //封装一级分类
        for (EduSubject eduSubject : eduSubjectsOne) {
            //单个one的数据
            OneSubject oneSubject = new OneSubject();
            //这样可以 但是太麻烦 如果有多个数据就炸了
//            oneSubject.setId(eduSubject.getId());
//            oneSubject.setTitle(eduSubject.getTitle());
            //将查询所有一级分类的onelist中的数据赋给单个one
            BeanUtils.copyProperties(eduSubject, oneSubject);
            //封装二级分类
            //创建用来存放每个one中的two对象的list
            ArrayList<TwoSubject> finalTwoSubjects = new ArrayList<>();
            for (int i = 0; i < eduSubjectsTwo.size(); i++) {
                //获取twolist中每一个two
                EduSubject eduSubjectTwo = eduSubjectsTwo.get(i);
                //获取当前正在遍历的two对象的ParentId 并跟one对象的id比较 如果相等 将此two对象存入finalTwoSubjects中
                String parentId = eduSubjectTwo.getParentId();
                if (oneSubject.getId().equals(parentId)) {
                    //用来存放单个two的数据
                    TwoSubject twoSubject = new TwoSubject();
                    //将twolist中的数据赋给two
                    BeanUtils.copyProperties(eduSubjectTwo, twoSubject);
                    finalTwoSubjects.add(twoSubject);
                }
            }
            //将遍历完的twolist 中ParentId 与此one对象的id相等的 添加到Children中
            oneSubject.setChildren(finalTwoSubjects);
            //最后返回的数据中的添加这次遍历中的one对象以及它包含的Children对象列表
            finalList.add(oneSubject);
        }
        return finalList;
    }
}
