package com.diodi.vodservice.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.diodi.commonutils.R;
import com.diodi.servicebase.exceptionhandler.GuliException;
import com.diodi.vodservice.service.VodService;
import com.diodi.vodservice.utils.ConstantVodUtils;
import com.diodi.vodservice.utils.InitObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author diodi
 * @create 2021-08-13-20:51
 */
@RestController
@CrossOrigin
@RequestMapping("/eduvod/video")
public class VodController {
    @Autowired
    private VodService vodService;

    /**
     * 上传视频到阿里云
     */
    @PostMapping("/uploadAliyunVideo")
    public R uploadAliyunVideo(MultipartFile file) {
        //返回上传视频的id
        String videoId = vodService.uploadVideoAliyun(file);
        return R.ok().data("videoId", videoId);
    }

    /**
     * 根据视频ID删除阿里云视频
     */
    @DeleteMapping("removeAlyVideo/{id}")
    public R removeAlyVideo(@PathVariable String id) {

        try {
            DefaultAcsClient client = InitObject.initVodClient(ConstantVodUtils.ACCESSKEY_ID,
                                                               ConstantVodUtils.ACCESSKEY_SECRET);
            DeleteVideoRequest request = new DeleteVideoRequest();
            request.setVideoIds(id);
            client.getAcsResponse(request);
            return R.ok();
        } catch (ClientException e) {
            e.printStackTrace();
            throw new GuliException(20001, "删除视频失败");
        }
    }

    /**
     * 删除多个阿里云视频的方法
     * @param videoIdList 多个视频ID的集合
     * @return
     */
    @DeleteMapping("deleteBatch")
    public R deleteBatch(@RequestParam("videoIdList") List<String> videoIdList) {
        vodService.removeMoreAlyVideo(videoIdList);
        return R.ok();
    }
}
