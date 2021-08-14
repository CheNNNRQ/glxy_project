package com.diodi.eduService.controller;

import com.diodi.commonutils.R;
import com.diodi.eduService.entity.EduVideo;
import com.diodi.eduService.service.EduVideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程小结视频 前端控制器
 * </p>
 * @author diodi
 * @since 2021-08-08
 */
@Api(tags = "课程小结")
@RestController
@CrossOrigin
@RequestMapping("/eduService/video")
public class EduVideoController {
    @Autowired
    EduVideoService videoService;

    /**
     * 添加小节
     * @param eduVideo 小节对象
     * @return
     */
    @ApiOperation("添加小节")
    @PostMapping("addVideo")
    public R addVideo(@RequestBody EduVideo eduVideo) {
        videoService.save(eduVideo);
        return R.ok();
    }

    /**
     * 根据id查询小节对象
     * @param id id
     * @return
     */
    @ApiOperation("根据id查询小节对象")
    @GetMapping("getVideoById/{id}")
    public R getVideoById(@PathVariable String id) {
        EduVideo byId = videoService.getById(id);
        return R.ok().data("video",byId);
    }

    /**
     * 修改小节
     * @param eduVideo 小节对象
     * @return
     */
    @ApiOperation("修改小节")
    @PostMapping("updateVideo")
    public R updateVideo(@RequestBody EduVideo eduVideo) {
        videoService.updateById(eduVideo);
        return R.ok();
    }

    // TODO 后面这个方法需要完善，删除小节的时候，同时也要把视频删除
    /**
     * 删除小节
     * @param id id
     * @return
     */
    @ApiOperation("删除小节")
    @DeleteMapping("deleteVideo/{id}")
    public R deleteVideo(@PathVariable String id) {
        videoService.removeById(id);
        return R.ok();
    }
}

