package com.diodi.eduorder.service;

import com.diodi.eduorder.entity.PayLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 支付日志表 服务类
 * </p>
 *
 * @author diodi
 * @since 2021-08-23
 */
public interface PayLogService extends IService<PayLog> {

    /**
     * 生成微信支付二维码的接口
     * @param orderNo
     * @return
     */
    Map<String, Object> createWxQrcode(String orderNo);

    /**
     * 查询支付状态
     * @param orderId
     * @return
     */
    Map<String, String> queryPayStatus(String orderId);

    /**
     * 添加支付记录到支付表 更新订单状态
     * @param map
     */
    void updateOrderStatus(Map<String, String> map);
}
