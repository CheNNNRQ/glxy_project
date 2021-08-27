package com.diodi.msmservice.controller;

import com.diodi.commonutils.R;
import com.diodi.commonutils.RandomUtil;
import com.diodi.msmservice.service.MsmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

/**
 * @author diodi
 * @create 2021-08-17-14:44
 */
@RestController
@RequestMapping("/api/msm")
public class MsmController {
    @Autowired
    private MsmService msmService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 发送邮件的方法
     * @param email 接收端的email
     * @return
     */
    @GetMapping(value = "/send/{email}")
    public R code(@PathVariable String email){
        String s = redisTemplate.opsForValue().get(email);
        // 从redis获取验证码 获取到直接返回
        if (!StringUtils.isEmpty(s)) {
            return R.ok();
        }
        //如果redis获取不到 发送邮件
        String code = RandomUtil.getFourBitRandom();
        Boolean isSend = msmService.send(code,email);
        if(isSend) {
            //发送成功后 把验证码放到redis中
            //设置有效时间
            redisTemplate.opsForValue().set(email, code, 5, TimeUnit.MINUTES);
            return R.ok();
        } else {
            return R.error().message("发送短信失败");
        }
    }
    //容联云短信
//    @Autowired
//    private RedisTemplate<String, String> redisTemplate;
//    @GetMapping(value = "/send/{phone}")
//    public R code(@PathVariable String phone) {
//        String code = redisTemplate.opsForValue().get(phone);
//        if(!StringUtils.isEmpty(code)) {
//            return R.ok();
//        }
//        code = RandomUtil.getFourBitRandom();
//        Map<String,Object> param = new HashMap<>();
//        param.put("code", code);
//        boolean isSend = msmService.send(phone, "SMS_180051135", param);
//        if(isSend) {
//            redisTemplate.opsForValue().set(phone, code, 5, TimeUnit.MINUTES);
//            return R.ok();
//        } else {
//            return R.error().message("发送短信失败");
//        }
//    }
}
