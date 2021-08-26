package com.diodi.staservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.diodi.commonutils.R;
import com.diodi.staservice.client.UcenterClient;
import com.diodi.staservice.entity.StatisticsDaily;
import com.diodi.staservice.mapper.StatisticsDailyMapper;
import com.diodi.staservice.service.StatisticsDailyService;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 * @author diodi
 * @since 2021-08-25
 */
@Service
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyMapper, StatisticsDaily> implements StatisticsDailyService {
    @Autowired
    UcenterClient ucenterClient;

    /**
     * 远程调用得到某一天的人数
     * 把获取的数据添加到数据库
     * @param day
     */
    @Override
    public void registerCount(String day) {
        //添加记录之前删除相同日期的数据
        QueryWrapper<StatisticsDaily> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("date_calculated", day);
        baseMapper.delete(queryWrapper);
        //远程调用得到某一天的人数
        R r = ucenterClient.countRegister(day);
        Integer countRegister = (Integer) r.getData().get("countRegister");
        //把获取的数据添加到数据库
        StatisticsDaily statisticsDaily = new StatisticsDaily();
        statisticsDaily.setDateCalculated(day);
        statisticsDaily.setRegisterNum(countRegister);
        statisticsDaily.setLoginNum(RandomUtils.nextInt(100, 200));
        statisticsDaily.setVideoViewNum(RandomUtils.nextInt(100, 200));
        statisticsDaily.setCourseNum(RandomUtils.nextInt(100, 200));
        baseMapper.insert(statisticsDaily);
    }

    /**
     * 图表显示,返回两部分数据,日期json数组,数量json数组
     * @param type
     * @param begin
     * @param end
     */
    @Override
    public Map<String, Object> getShowData(String type,
                                           String begin,
                                           String end) {
        QueryWrapper<StatisticsDaily> queryWrapper = new QueryWrapper<>();
        //查询日期在begin end之间的
        queryWrapper.between("date_calculated", begin, end);
        queryWrapper.orderByAsc("date_calculated");
        //查询列 这里表示只查询日期和类型 类型来自前端的数据 和数据库的列名一样
        queryWrapper.select("date_calculated", type);
        List<StatisticsDaily> staList = baseMapper.selectList(queryWrapper);
        //返回日期和数量 分两部分存储
        //前端要求json,对应后端的list集合
        //创建两个list集合 日期 和数量
        ArrayList<String> dateCalculatedList = new ArrayList<>();
        ArrayList<Integer> numDataList = new ArrayList<>();
        for (StatisticsDaily statisticsDaily : staList) {
            dateCalculatedList.add(statisticsDaily.getDateCalculated());
            switch (type) {
                case "register_num":
                    numDataList.add(statisticsDaily.getRegisterNum());
                    break;
                case "login_num":
                    numDataList.add(statisticsDaily.getLoginNum());
                    break;
                case "video_view_num":
                    numDataList.add(statisticsDaily.getVideoViewNum());
                    break;
                case "course_num":
                    numDataList.add(statisticsDaily.getCourseNum());
                    break;
                default:
                    break;
            }
        }
        HashMap<String, Object> stringObjectHashMap = new HashMap<>();
        stringObjectHashMap.put("dateCalculatedList",dateCalculatedList);
        stringObjectHashMap.put("numDataList",numDataList);
        return stringObjectHashMap;
    }
}
