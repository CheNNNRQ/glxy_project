package com.diodi.eduorder.client;

import com.diodi.commonutils.UcenterMemberVo;
import org.springframework.stereotype.Component;

/**
 * @author diodi
 * @create 2021-08-22-20:46
 */
@Component
public class UcenterClientImpl implements UcenterClient {
    /**
     * 根据用户id查询信息 封装到UcenterMemberVo给远程调用类使用
     * @param memberId 用户id
     * @return
     */
    @Override
    public UcenterMemberVo getMemberInfoById(String memberId) {
        return null;
    }
}
