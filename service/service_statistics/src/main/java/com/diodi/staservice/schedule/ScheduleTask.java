package com.diodi.staservice.schedule;

import com.diodi.commonutils.DateUtil;
import com.diodi.staservice.service.StatisticsDailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author diodi
 * @create 2021-08-25-20:31
 */
@Component
public class ScheduleTask {
    @Autowired
    StatisticsDailyService dailyService;
//    @Scheduled(cron = "0/5 * * * * ?")
    public void task1(){
        System.out.println("******************task1执行了");
    }

    /**
     * 在每天的中午12点把前一天数据进行数据查询添加
     */
    @Scheduled(cron = "0 0 16 * * ? ")
    public void task2(){
        System.out.println("******************task2执行了");
        dailyService.registerCount(DateUtil.formatDate(DateUtil.addDays(new Date(), -1)));
    }
}
