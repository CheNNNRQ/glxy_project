package com.diodi.eduService.controller;

import com.diodi.commonutils.R;
import com.diodi.eduService.client.VodClient;
import com.diodi.eduService.entity.EduVideo;
import com.diodi.eduService.service.EduVideoService;
import com.diodi.servicebase.exceptionhandler.GuliException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
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
@RequestMapping("/eduService/video")
public class EduVideoController {
    @Autowired
    EduVideoService videoService;

    @Autowired
    VodClient vodClient;

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
        return R.ok().data("video", byId);
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


    /**
     * 删除小节
     * @param id id
     * @return
     */
    @ApiOperation("删除小节")
    @DeleteMapping("deleteVideo/{id}")
    public R deleteVideo(@PathVariable String id) {
        //根据小节id获取视频id,调用方法实现视频删除
        EduVideo byId = videoService.getById(id);
        String videoSourceId = byId.getVideoSourceId();
        //根据视频ID远程调用微服务实现视频删除
        if (!StringUtils.isEmpty(videoSourceId)) {
            R r = vodClient.removeAlyVideo(videoSourceId);
            if (r.getCode()==20001) {
                throw new GuliException(20001,"删除视频失败熔断");
            }
        }
        //注意顺序 是先删视频 再删小节
        videoService.removeById(id);
        return R.ok();
    }

}

