package com.diodi.servicebase.controller;

import com.diodi.commonutils.R;
import com.diodi.servicebase.service.OssService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author diodi
 * @create 2021-08-05-15:24
 */
@Api(description="阿里云文件管理")
@RestController
@RequestMapping("/eduoss/fileoss")
public class OssController {
    @Autowired
    OssService service;

    @ApiOperation(value = "文件上传")
    @PostMapping("upload")
    public R uploadIssFile(MultipartFile file){
        String url  = service.upLoadFileAvatar(file);
        return R.ok().data("url",url);
    }
}
