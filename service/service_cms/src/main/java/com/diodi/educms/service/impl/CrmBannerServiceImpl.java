package com.diodi.educms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.diodi.educms.entity.CrmBanner;
import com.diodi.educms.mapper.CrmBannerMapper;
import com.diodi.educms.service.CrmBannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author diodi
 * @since 2021-08-15
 */
@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements CrmBannerService {
    /**
     * 查询所有banner
     * @return
     */
    @Override
    public List<CrmBanner> selectAllBanner() {
        QueryWrapper<CrmBanner> crmBannerQueryWrapper = new QueryWrapper<>();
        //降序
        crmBannerQueryWrapper.orderByDesc("sort");
        //语句拼接 查询前4条记录
        crmBannerQueryWrapper.last("limit 4");
        List<CrmBanner> crmBanners = baseMapper.selectList(crmBannerQueryWrapper);
        return crmBanners;
    }
}
