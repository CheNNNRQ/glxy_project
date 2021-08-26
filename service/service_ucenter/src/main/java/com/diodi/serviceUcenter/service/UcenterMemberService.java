package com.diodi.serviceUcenter.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.diodi.serviceUcenter.entity.UcenterMember;
import com.diodi.serviceUcenter.vo.RegisterVo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author diodi
 * @since 2021-08-18
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    /**
     * 用户登录
     * @param ucenterMember 用户对象
     * @return token
     */
    String login(UcenterMember ucenterMember);

    /**
     * 用户注册
     * @param registerVo 用户对象
     * @return
     */
    void register(RegisterVo registerVo);

    /**
     * 判断数据库是否存在相同的微信内容
     * @param openid
     * @return
     */
    UcenterMember getMemberByOpenId(String openid);

    /**
     * 远程调用
     * 查询某一天的注册人数
     * @param day
     * @return
     */
    Integer countRegisterDay(String day);
}
