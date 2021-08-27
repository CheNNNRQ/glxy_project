package com.diodi.eduorder.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.diodi.commonutils.JwtUtils;
import com.diodi.commonutils.R;
import com.diodi.eduorder.entity.Order;
import com.diodi.eduorder.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 * @author diodi
 * @since 2021-08-23
 */
@RestController
@RequestMapping("/eduorder/order")
public class OrderController {
    @Autowired
    OrderService orderService;

    /**
     * 生成订单
     * @param courseId 课程id
     * @return
     */
    @PostMapping("createOrder/{courseId}")
    public R createOrder(@PathVariable String courseId,
                         HttpServletRequest request) {
        //获取用户id
        String numberId = JwtUtils.getMemberIdByJwtToken(request);
        //创建订单 返回订单号
        String orderNumber = orderService.createOrders(courseId, numberId);
        return R.ok().data("orderId", orderNumber);
    }

    /**
     * 根据订单号查询订单信息
     * @param orderId 订单号
     * @return 订单信息
     */
    @GetMapping("getOrderInfo/{orderId}")
    public R getOrderInfo(@PathVariable String orderId) {
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_no", orderId);
        Order order = orderService.getOne(queryWrapper);
        return R.ok().data("item", order);
    }

    /**
     * 根据课程id和用户id查询订单表中订单的状态
     * @param memberId 用户id
     * @param courseId 课程id
     * @return
     */
    @GetMapping("isBuyCourse/{memberId}/{courseId}")
    public Boolean isBuyCourse(@PathVariable String memberId,
                               @PathVariable String courseId) {
        QueryWrapper<Order> orderQueryWrapper = new QueryWrapper<>();
        orderQueryWrapper.eq("course_id", courseId);
        orderQueryWrapper.eq("member_id", memberId);
        orderQueryWrapper.eq("status", 1);
        int count = orderService.count(orderQueryWrapper);
        System.out.println(count);
        if (count > 0) {
            return true;
        } else {
            return false;
        }
    }
}

