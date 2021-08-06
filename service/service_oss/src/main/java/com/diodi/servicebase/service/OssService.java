package com.diodi.servicebase.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author diodi
 * @create 2021-08-05-15:24
 */
public interface OssService {
    String upLoadFileAvatar(MultipartFile file);
}
