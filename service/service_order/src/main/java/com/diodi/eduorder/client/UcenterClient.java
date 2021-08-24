package com.diodi.eduorder.client;

import com.diodi.commonutils.UcenterMemberVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author diodi
 * @create 2021-08-22-20:44
 */
@Component
@FeignClient(value = "service-ucenter",fallback = UcenterClientImpl.class)
public interface UcenterClient {

    /**
     * 根据用户id查询信息 封装到UcenterMemberVo给远程调用类使用
     * @param memberId 用户id
     * @return
     */
    @PostMapping("/serviceUcenter/member/getMemberInfoById/{memberId}")
    public UcenterMemberVo getMemberInfoById(@PathVariable("memberId") String memberId);
}
