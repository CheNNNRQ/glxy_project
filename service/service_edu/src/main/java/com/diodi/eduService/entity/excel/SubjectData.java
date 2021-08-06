package com.diodi.eduService.entity.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author diodi
 * @create 2021-08-05-22:01
 */
@Data
@ToString
public class SubjectData {

    //一级分类
    @ExcelProperty(index = 0,value = "一级分类")
    private String oneSubjectName;

    //二级分类
    @ExcelProperty(index = 1,value = "二级分类")
    private String twoSubjectName;
}
