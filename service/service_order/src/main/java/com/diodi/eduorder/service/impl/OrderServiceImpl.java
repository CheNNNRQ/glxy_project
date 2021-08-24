package com.diodi.eduorder.service.impl;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.diodi.commonutils.CourseWebVoOrder;
import com.diodi.commonutils.UcenterMemberVo;
import com.diodi.eduorder.client.EduClient;
import com.diodi.eduorder.client.UcenterClient;
import com.diodi.eduorder.entity.Order;
import com.diodi.eduorder.mapper.OrderMapper;
import com.diodi.eduorder.service.OrderService;
import com.diodi.servicebase.exceptionhandler.GuliException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author diodi
 * @since 2021-08-23
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {
    @Autowired
    EduClient eduClient;
    @Autowired
    UcenterClient ucenterClient;
    /**
     * 创建订单 返回订单号
     * <p>通过远程调用实现</p>
     * @param courseId 课程id
     * @param numberId 用户id
     * @return 订单号
     */
    @Override
    public String createOrders(String courseId,
                               String numberId) {
        if (StringUtils.isEmpty(numberId)){
            throw new GuliException(20001,"未登录");
        }
        //获取用户信息
        UcenterMemberVo memberInfoById = ucenterClient.getMemberInfoById(numberId);
        String nickname = memberInfoById.getNickname();
        String email = memberInfoById.getMobile();

        //获取课程信息
        CourseWebVoOrder courseOrderInfo = eduClient.getCourseOrderInfo(courseId);
        String title = courseOrderInfo.getTitle();
        String cover = courseOrderInfo.getCover();
        String teacherName = courseOrderInfo.getTeacherName();
        BigDecimal price = courseOrderInfo.getPrice();
        //向表中添加订单
        Order order = new Order();
        order.setOrderNo(UUID.randomUUID().toString().replace("-", ""));
        order.setCourseId(courseId);
        order.setCourseTitle(title);
        order.setCourseCover(cover);
        order.setTeacherName(teacherName);
        order.setTotalFee(price);
        order.setPayType(1);
        order.setStatus(0);

        order.setMemberId(numberId);
        order.setNickname(nickname);
        order.setEmail(email);
        baseMapper.insert(order);
        //生成订单号
        return order.getOrderNo();
    }
}
