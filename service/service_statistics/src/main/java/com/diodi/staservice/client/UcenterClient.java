package com.diodi.staservice.client;

import com.diodi.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author diodi
 * @create 2021-08-25-10:56
 */
@Component
@FeignClient(value = "service-ucenter" ,fallback = UcenterClientImpl.class)
public interface UcenterClient {
    /**
     * <p>查询某一天的注册人数</p>
     * <p>用于给statistics_daily远程调用使用</p>
     * <p>编写sql查询</p>
     * @param day 日期
     * @return 某一天的注册人数
     */
    @GetMapping("/serviceUcenter/member/countRegister/{day}")
    public R countRegister(@PathVariable("day") String day);
}
