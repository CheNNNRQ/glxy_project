package com.diodi.vodservice.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author diodi
 * @create 2021-08-13-20:52
 */
public interface VodService {
    /**
     * 上传视频到阿里云
     * @param file
     * @return
     */
    String uploadVideoAliyun(MultipartFile file);

    /**
     * 删除多个阿里云视频的方法
     * @param videoIdList 多个视频ID的集合
     * @return
     */
    void removeMoreAlyVideo(List<String> videoIdList);
}
