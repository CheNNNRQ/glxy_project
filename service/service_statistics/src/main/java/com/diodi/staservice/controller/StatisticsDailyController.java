package com.diodi.staservice.controller;


import com.diodi.commonutils.R;
import com.diodi.staservice.service.StatisticsDailyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 * @author diodi
 * @since 2021-08-25
 */
@Api(tags = "网站统计日数据")
@RestController
@CrossOrigin
@RequestMapping("/staservice/sta")
public class StatisticsDailyController {
    @Autowired
    StatisticsDailyService dailyService;

    /**
     * 远程调用得到某一天的人数
     * @param day
     * @return
     */
    @ApiOperation("远程调用得到某一天的人数 并添加到数据库")
    @GetMapping("registerCount/{day}")
    public R registerCount(@PathVariable String day) {
        dailyService.registerCount(day);
        return R.ok();
    }

    /**
     * 图表显示,返回两部分数据,日期json数组,数量json数组
     * @param type 数据类型
     * @param begin 开始时间
     * @param end 结束时间
     * @return 日期json数组,数量json数组
     */
    @GetMapping("showData/{type}/{begin}/{end}")
    public R showData(@PathVariable String type,
                      @PathVariable String begin,
                      @PathVariable String end) {
        Map<String, Object> map = dailyService.getShowData(type,begin,end);
        return R.ok().data(map);
    }
}

