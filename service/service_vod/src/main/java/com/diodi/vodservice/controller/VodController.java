package com.diodi.vodservice.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.diodi.commonutils.R;
import com.diodi.servicebase.exceptionhandler.GuliException;
import com.diodi.vodservice.service.VodService;
import com.diodi.vodservice.utils.ConstantVodUtils;
import com.diodi.vodservice.utils.InitObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 阿里云视频相关
 * @author diodi
 * @create 2021-08-13-20:51
 */
@Api(tags = "阿里云视频相关")
@RestController
@RequestMapping("/eduvod/video")
public class VodController {
    @Autowired
    private VodService vodService;

    /**
     * 上传视频到阿里云
     */
    @ApiOperation("上传视频到阿里云")
    @PostMapping("/uploadAliyunVideo")
    public R uploadAliyunVideo(MultipartFile file) {
        //返回上传视频的id
        String videoId = vodService.uploadVideoAliyun(file);
        return R.ok().data("videoId", videoId);
    }

    /**
     * 根据视频ID删除阿里云视频
     */
    @ApiOperation("根据视频ID删除阿里云视频")
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
    @ApiOperation("删除多个阿里云视频的方法")
    @DeleteMapping("deleteBatch")
    public R deleteBatch(@RequestParam("videoIdList") List<String> videoIdList) {
        vodService.removeMoreAlyVideo(videoIdList);
        return R.ok();
    }

    /**
     * 根据视频id获取视频凭证
     * @param id
     * @return
     */
    @ApiOperation("根据视频id获取视频凭证")
    @GetMapping("getPlayAuth/{id}")
    public R getPlayAuth(@PathVariable String id) {
        try {
            //创建初始化对象
            DefaultAcsClient Client = InitObject.initVodClient(ConstantVodUtils.ACCESSKEY_ID,
                                                               ConstantVodUtils.ACCESSKEY_SECRET);
            //创建获取凭证的request和resp对象
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            //向request设置视频id
            request.setVideoId(id);
            //调用方法得到凭证
            GetVideoPlayAuthResponse acsResponse = Client.getAcsResponse(request);
            String playAuth = acsResponse.getPlayAuth();
            return R.ok().data("playAuth",playAuth);
        } catch (ClientException e) {
            throw new GuliException(20001,"获取凭证失败");
        }
    }
}
