package com.diodi.eduService.client;

import org.springframework.stereotype.Component;

/**
 * @author diodi
 * @create 2021-08-24-18:17
 */
@Component
public class OrderClientImpl implements OrderClient {
    /**
     * 根据课程id和用户id查询订单表中订单的状态
     * @param memberId 用户id
     * @param courseId 课程id
     * @return
     */
    @Override
    public Boolean isBuyCourse(String memberId,
                               String courseId) {
        return null;
    }
}
