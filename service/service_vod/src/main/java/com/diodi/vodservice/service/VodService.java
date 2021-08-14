package com.diodi.vodservice.service;

import org.springframework.web.multipart.MultipartFile;

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
}
