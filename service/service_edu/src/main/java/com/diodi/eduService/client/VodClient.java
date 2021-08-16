package com.diodi.eduService.client;

import com.diodi.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author diodi
 * @create 2021-08-14-17:57
 */
@Component
@FeignClient(value = "service-vod" , fallback = VodClientImpl.class)
public interface VodClient {

    /**
     * 根据视频ID删除阿里云视频
     * @param id delete id
     * @return ok
     */
    @DeleteMapping("/eduvod/video/removeAlyVideo/{id}")
    public R removeAlyVideo(@PathVariable("id") String id);

    /**
     * 删除多个阿里云视频的方法
     * @param videoIdList 多个视频ID的集合
     * @return
     */
    @DeleteMapping("/eduvod/video/deleteBatch")
    public R deleteBatch(@RequestParam("videoIdList") List<String> videoIdList);
}
