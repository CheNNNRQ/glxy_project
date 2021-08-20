package com.diodi.serviceUcenter.vo;

import lombok.Data;

/**
 * 注册对象
 * @author diodi
 * @create 2021-08-18-13:43
 */
@Data
public class RegisterVo {
    /**
     * 用户名
     */
    private String nickname;

    private String email;

    private String password;

    private String code;
}
