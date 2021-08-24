package com.diodi.eduorder.service;

import com.diodi.eduorder.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author diodi
 * @since 2021-08-23
 */
public interface OrderService extends IService<Order> {

    /**
     * 创建订单 返回订单号
     * @param courseId 课程id
     * @param numberId 用户id
     * @return 订单号
     */
    String createOrders(String courseId,
                        String numberId);
}
