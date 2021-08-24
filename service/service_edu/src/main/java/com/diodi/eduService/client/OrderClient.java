package com.diodi.eduService.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author diodi
 * @create 2021-08-24-18:16
 */
@Component
@FeignClient(value = "service-order" ,fallback = OrderClientImpl.class)
public interface OrderClient {
    /**
     * 根据课程id和用户id查询订单表中订单的状态
     * @param memberId 用户id
     * @param courseId 课程id
     * @return
     */
    @GetMapping("/eduorder/order/isBuyCourse/{memberId}/{courseId}")
    public Boolean isBuyCourse(@PathVariable("memberId") String memberId,
                               @PathVariable("courseId") String courseId);
}
