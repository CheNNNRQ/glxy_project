package com.diodi.eduorder.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.diodi.eduorder.entity.Order;
import com.diodi.eduorder.entity.PayLog;
import com.diodi.eduorder.mapper.PayLogMapper;
import com.diodi.eduorder.service.OrderService;
import com.diodi.eduorder.service.PayLogService;
import com.diodi.eduorder.utils.HttpClient;
import com.diodi.servicebase.exceptionhandler.GuliException;
import com.github.wxpay.sdk.WXPayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 支付日志表 服务实现类
 * </p>
 * @author diodi
 * @since 2021-08-23
 */
@Service
public class PayLogServiceImpl extends ServiceImpl<PayLogMapper, PayLog> implements PayLogService {
    @Autowired
    private OrderService orderService;

    @Override
    public Map<String, Object> createWxQrcode(String orderNo) {
        try {
            //根据订单id获取订单信息
            QueryWrapper<Order> wrapper = new QueryWrapper<>();
            wrapper.eq("order_no", orderNo);
            Order order = orderService.getOne(wrapper);
            Map m = new HashMap();
            //1、设置支付参数
            m.put("appid","wx74862e0dfcf69954");
            m.put("mch_id", "1558950191");
            m.put("nonce_str", WXPayUtil.generateNonceStr());
            m.put("body", order.getCourseTitle()); //课程标题
            m.put("out_trade_no", orderNo); //订单号
            m.put("total_fee", order.getTotalFee().multiply(new BigDecimal("100")).longValue()+"");
            m.put("spbill_create_ip", "127.0.0.1");
            m.put("notify_url", "http://guli.shop/api/order/weixinPay/weixinNotify\n");
            m.put("trade_type", "NATIVE");
            //2、HTTPClient来根据URL访问第三方接口并且传递参数
            HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/unifiedorder");
            //client设置参数
            client.setXmlParam(WXPayUtil.generateSignedXml(m,"T6m9iK73b0kn9g5v426MKfHQH7X8rKwb"));
            client.setHttps(true);
            client.post();
            //3、返回第三方的数据
            String xml = client.getContent();
            Map<String, String> resultMap = WXPayUtil.xmlToMap(xml);
            //4、封装返回结果集
            Map map = new HashMap<>();
            map.put("out_trade_no", orderNo);
            map.put("course_id", order.getCourseId());
            map.put("total_fee", order.getTotalFee());
            map.put("result_code", resultMap.get("result_code"));
            map.put("code_url", resultMap.get("code_url"));
            //微信支付二维码2小时过期，可采取2小时未支付取消订单
            //redisTemplate.opsForValue().set(orderNo, map, 120,TimeUnit.MINUTES);
            return map;
        } catch (Exception e) {
            throw new GuliException(20001, "生成失败");
        }
    }

    /**
     * 查询支付状态
     * @param orderId
     * @return
     */
    @Override
    public Map<String, String> queryPayStatus(String orderId) {
        try {
            //1、封装参数
            Map m = new HashMap<>();
            m.put("appid", "wx74862e0dfcf69954");
            m.put("mch_id", "1558950191");
            m.put("out_trade_no", orderId);
            m.put("nonce_str", WXPayUtil.generateNonceStr());
            //2、发送httpClient请求
            HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/orderquery");
            client.setHttps(true);
            client.setXmlParam(WXPayUtil.generateSignedXml(m, "T6m9iK73b0kn9g5v426MKfHQH7X8rKwb"));//通过商户key加密
            client.post();
            //3、返回第三方的数据
            String xml = client.getContent();
            //4、转成Map
            Map<String, String> resultMap = WXPayUtil.xmlToMap(xml);

            //5、返回
            return resultMap;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 添加支付记录到支付表 更新订单状态
     * @param map
     */
    @Override
    public void updateOrderStatus(Map<String, String> map) {
        //从map获取订单号
        String orderNo = map.get("out_trade_no");
        //根据订单id获取订单信息
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no", orderNo);
        Order order = orderService.getOne(wrapper);
        //更新订单表订单状态
        if (order.getStatus() == 1) {
            return;
        }
        //更新订单表中的订单状态
        order.setStatus(1);
        orderService.updateById(order);

        //向支付表里添加支付记录
        PayLog tPayLog = new PayLog();
        //支付订单号
        tPayLog.setOrderNo(orderNo);
        //支付时间
        tPayLog.setPayTime(new Date());
        //支付类型
        tPayLog.setPayType(1);
        //总金额(分)
        tPayLog.setTotalFee(order.getTotalFee());
        //支付状态
        tPayLog.setTradeState(map.get("trade_state"));
        //订单流水号
        tPayLog.setTransactionId(map.get("transaction_id"));
        tPayLog.setAttr(JSONObject.toJSONString(map));
        baseMapper.insert(tPayLog);

    }
}

