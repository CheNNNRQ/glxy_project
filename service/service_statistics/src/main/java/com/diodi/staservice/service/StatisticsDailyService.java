package com.diodi.staservice.service;

import com.diodi.staservice.entity.StatisticsDaily;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author diodi
 * @since 2021-08-25
 */
public interface StatisticsDailyService extends IService<StatisticsDaily> {

    /**
     * 远程调用得到某一天的人数
     * @param day
     */
    void registerCount(String day);

    /**
     * 图表显示,返回两部分数据,日期json数组,数量json数组
     */
    Map<String, Object> getShowData(String type,
                                    String begin,
                                    String end);
}
