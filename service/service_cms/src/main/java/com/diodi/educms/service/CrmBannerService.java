package com.diodi.educms.service;

import com.diodi.educms.entity.CrmBanner;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author diodi
 * @since 2021-08-15
 */
public interface CrmBannerService extends IService<CrmBanner> {

    /**
     * 查询所有banner
     * @return
     */
    List<CrmBanner> selectAllBanner();
}
