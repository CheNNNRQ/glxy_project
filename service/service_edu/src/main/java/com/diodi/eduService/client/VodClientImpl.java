package com.diodi.eduService.client;

import com.diodi.commonutils.R;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author diodi
 * @create 2021-08-15-13:05
 */
@Component
public class VodClientImpl implements VodClient{
    /**
     * 根据视频ID删除阿里云视频
     * @param id delete id
     * @return ok
     */
    @Override
    public R removeAlyVideo(String id) {
        return R.error().message("根据视频ID删除阿里云视频失败 熔断");
    }

    /**
     * 删除多个阿里云视频的方法
     * @param videoIdList 多个视频ID的集合
     * @return
     */
    @Override
    public R deleteBatch(List<String> videoIdList) {
        return R.error().message("删除多个阿里云视频的方法 熔断");
    }
}
