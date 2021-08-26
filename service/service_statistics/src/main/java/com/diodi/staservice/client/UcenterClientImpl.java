package com.diodi.staservice.client;

import com.diodi.commonutils.R;
import org.springframework.stereotype.Component;

/**
 * @author diodi
 * @create 2021-08-25-10:57
 */
@Component
public class UcenterClientImpl implements UcenterClient {

    /**
     * <p>查询某一天的注册人数</p>
     * <p>用于给statistics_daily远程调用使用</p>
     * <p>编写sql查询</p>
     * @param day 日期
     * @return 某一天的注册人数
     */
    @Override
    public R countRegister(String day) {
        return R.error().message("查询失败");
    }
}
