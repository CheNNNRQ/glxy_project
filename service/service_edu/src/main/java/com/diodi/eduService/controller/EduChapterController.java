package com.diodi.eduService.controller;

import com.diodi.commonutils.R;
import com.diodi.eduService.entity.EduChapter;
import com.diodi.eduService.entity.chapter.ChapterVo;
import com.diodi.eduService.service.EduChapterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 章节 前端控制器
 * </p>
 * @author diodi
 * @since 2021-08-08
 */
@Api(tags = "课程大纲列表功能")
@RestController
@CrossOrigin
@RequestMapping("/eduService/chapter")
public class EduChapterController {
    @Autowired
    EduChapterService eduChapterService;

    /**
     * 嵌套章节数据列表
     * @param courseId 课程id
     * @return 课程下的章节列表
     */
    @ApiOperation("嵌套章节数据列表")
    @GetMapping("nestedList/{courseId}")
    public R nestedListByCourseId(@PathVariable String courseId) {
        List<ChapterVo> chapterVoList = eduChapterService.nestedList(courseId);
        return R.ok().data("items", chapterVoList);
    }

    /**
     * 添加章节
     * @param eduChapter 章节对象
     * @return
     */
    @ApiOperation("添加章节")
    @PostMapping("addChapter")
    public R addChapter(@RequestBody EduChapter eduChapter) {
        eduChapterService.save(eduChapter);
        return R.ok();
    }

    /**
     * 根据章节id查询
     * @param chapterId 章节id
     * @author diodi
     * @returns com.diodi.commonutils.R
     * @Date 20:51 2021/8/10
     */
    @ApiOperation("根据章节id查询")
    @GetMapping("getChapterInfo/{chapterId}")
    public R geChapterInfo(@PathVariable String chapterId) {
        EduChapter byId = eduChapterService.getById(chapterId);
        return R.ok().data("chapter", byId);
    }

    /**
     * 修改章节
     * @param eduChapter 章节对象
     * @return
     */
    @ApiOperation("修改章节")
    @PostMapping("updateChapter")
    public R updateChapter(@RequestBody EduChapter eduChapter) {
        eduChapterService.updateById(eduChapter);
        return R.ok();
    }

    /**
     * 删除章节 这里采用的删除方式是如果章节下有小节 不让删除 反之可以删除
     * @param chapterId 章节id
     * @return
     */
    @ApiOperation("删除章节")
    @DeleteMapping("deleteChapter/{chapterId}")
    public R deleteChapter(@PathVariable String chapterId) {
        Boolean ok = eduChapterService.deleteChapter(chapterId);
        if (ok) {
            return R.ok();
        } else {
            return R.error();
        }
    }
}

