package com.diodi.educms.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.diodi.commonutils.R;
import com.diodi.educms.entity.CrmBanner;
import com.diodi.educms.service.CrmBannerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 管理员使用的banner接口
 * </p>
 * @author diodi
 * @since 2021-08-15
 */
@Api(tags = "管理员使用的banner接口")
@RestController
@CrossOrigin
@RequestMapping("/educms/bannerAdmin")
public class BannerAdminController {
    @Autowired
    CrmBannerService bannerService;

    //TODO 完成管理员用户的 对banner的增删改查管理

    /**
     * 分页查询banner
     * @param page  第几页
     * @param limit 每页记录数
     * @return
     */
    @ApiOperation("分页查询banner")
    @GetMapping("pageBanner/{page}/{limit}")
    public R pageBanner(@PathVariable Long page,
                        @PathVariable Long limit) {
        Page<CrmBanner> crmBannerPage = new Page<>(page, limit);
        bannerService.page(crmBannerPage, null);
        long total = crmBannerPage.getTotal();
        List<CrmBanner> records = crmBannerPage.getRecords();
        return R.ok().data("total", total).data("records", records);
    }

    /**
     * 增
     * @param crmBanner
     * @return
     */
    @ApiOperation("增")
    @PostMapping("addBanner")
    public R addBanner(@RequestBody CrmBanner crmBanner) {
        bannerService.save(crmBanner);
        return R.ok();
    }

    /**
     * 删
     * @param id
     * @return
     */
    @ApiOperation("删")
    @DeleteMapping("deleteBanner/{id}")
    public R deleteBanner(@PathVariable String id) {
        bannerService.removeById(id);
        return R.ok();
    }

    /**
     * 查
     * @param id
     * @return
     */
    @ApiOperation("查")
    @GetMapping("getBanner/{id}")
    public R getBanner(@PathVariable String id) {
        CrmBanner byId = bannerService.getById(id);
        return R.ok().data("item", byId);
    }

    /**
     * 改
     * @param crmBanner
     * @return
     */
    @ApiOperation("改")
    @PostMapping("updateBanner")
    public R updateBanner(@RequestBody CrmBanner crmBanner) {
        bannerService.updateById(crmBanner);
        return R.ok();
    }
}

