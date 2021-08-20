package com.diodi.msmservice.service;

/**
 * @author diodi
 * @create 2021-08-17-14:44
 */
public interface MsmService {
    /**
     * 发送邮件的方法
     * @param email 接收端的email
     * @return
     */
    Boolean send(String code,
                 String email);
//    boolean send(String phone,
//                 String num,
//                 Map<String, Object> param);
}
