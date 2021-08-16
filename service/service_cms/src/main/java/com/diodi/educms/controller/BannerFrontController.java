package com.diodi.educms.controller;

import com.diodi.commonutils.R;
import com.diodi.educms.entity.CrmBanner;
import com.diodi.educms.service.CrmBannerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 用户使用的前台 banner接口
 * @author diodi
 * @create 2021-08-15-21:37
 */
@Api(tags = "用户使用的前台 banner接口")
@RestController
@CrossOrigin
@RequestMapping("/educms/bannerFront")
public class BannerFrontController {

    @Autowired
    CrmBannerService bannerService;

    /**
     * 查询所有banner - 前4条
     * @return
     */
    @Cacheable(key = "'selectIndexList'",value = "banner")
    @ApiOperation("查询所有banner")
    @GetMapping("getAllBanner")
    public R getAllBanner() {
        List<CrmBanner> list = bannerService.selectAllBanner();
        return R.ok().data("list", list);
    }

}
