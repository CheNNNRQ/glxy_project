package com.diodi.eduorder.controller;

import com.diodi.commonutils.R;
import com.diodi.eduorder.service.PayLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 * @author diodi
 * @since 2021-08-23
 */
@RestController
@RequestMapping("/eduorder/paylog")
public class PayLogController {
    @Autowired
    private PayLogService payLogService;

    /**
     * 根据订单号，生成微信支付二维码的接口
     * @param orderNo
     * @return
     */
    @GetMapping("/createWxQRcode/{orderNo}")
    public R createWxQRcode(@PathVariable String orderNo) {
        //返回信息，包含二维码地址、其他信息
        Map<String, Object> map = payLogService.createWxQrcode(orderNo);
//        System.out.println("@@@@@@@@@@二维码的集合"+map);
        return R.ok().data(map);
    }

    /**
     * 根据订单号查询订单支付状态
     * @param orderId
     * @return
     */
    @GetMapping("queryPayStatus/{orderId}")
    public R queryPayStatus(@PathVariable String orderId) {
        //查询支付状态
        Map<String, String> map = payLogService.queryPayStatus(orderId);
        if (map == null) {
            return R.error().message("支付失败");
        }
        //如果map不为空 通过map读取订单状态
        if ("SUCCESS".equals(map.get("trade_state"))) {
            //添加支付记录到支付表 更新订单状态
            payLogService.updateOrderStatus(map);
            return R.ok().message("支付成功");
        }
        return R.ok().code(25000).message("支付中");
    }
}

