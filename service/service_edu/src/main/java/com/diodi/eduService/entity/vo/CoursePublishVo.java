package com.diodi.eduService.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author diodi
 * @create 2021-08-11-21:13
 */
@Data
public class CoursePublishVo {
    private static final long serialVersionUID = 1L;
    /**
     *  课程id
     */
    @ApiModelProperty(value = "课程ID")
    private String id;
    /**
     * 课程名称
     */
    @ApiModelProperty(value = "课程名称")
    private String title;
    /**
     * 封面
     */
    @ApiModelProperty(value = "封面")
    private String cover;

    /**
     * 课时数
     */
    @ApiModelProperty(value = "课时数")
    private Integer lessonNum;

    /**
     * 一级分类
     */
    @ApiModelProperty(value = "一级分类")
    private String subjectLevelOne;
    /**
     * 二级分类
     */
    @ApiModelProperty(value = "二级分类")
    private String subjectLevelTwo;

    /**
     * 讲师名称
     */
    @ApiModelProperty(value = "讲师名称")
    private String teacherName;

    /**
     * 价格 ，只用于显示
     */
    @ApiModelProperty(value = "价格 ，只用于显示")
    private String price;
}
