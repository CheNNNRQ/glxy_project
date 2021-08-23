package com.diodi.serviceUcenter.controller;

import com.diodi.commonutils.JwtUtils;
import com.diodi.commonutils.R;
import com.diodi.commonutils.UcenterMemberVo;
import com.diodi.serviceUcenter.entity.UcenterMember;
import com.diodi.serviceUcenter.service.UcenterMemberService;
import com.diodi.serviceUcenter.vo.RegisterVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 * @author diodi
 * @since 2021-08-18
 */
@RestController
@Api(tags = "用户登录和注册")
@CrossOrigin
@RequestMapping("/serviceUcenter/member")
public class UcenterMemberController {

    @Autowired
    UcenterMemberService memberService;

    /**
     * 用户登录
     * @param ucenterMember 用户对象
     * @return token
     */
    @ApiOperation("用户登录")
    @PostMapping("login")
    public R login(@RequestBody UcenterMember ucenterMember) {
        //返回token ,使用jwt生成
        String token = memberService.login(ucenterMember);
        return R.ok().data("token" ,token);
    }

    /**
     * 用户注册
     * @param registerVo 用户对象
     * @return
     */
    @ApiOperation("用户注册")
    @PostMapping("register")
    public R register(@RequestBody RegisterVo registerVo){
        memberService.register(registerVo);
        return R.ok();
    }

    /**
     * 根据token获取用户信息
     * @param request
     * @return
     */
    @GetMapping("getMemberInfo")
    @ApiOperation("根据token获取用户信息")
    public R getMemberInfo(HttpServletRequest request) {
        //工具类的方法 传入前台的HttpServletRequest对象从中获取id值
        String id = JwtUtils.getMemberIdByJwtToken(request);
        UcenterMember member = memberService.getById(id);
        return R.ok().data("userInfo", member);
    }

    /**
     * 根据用户id查询信息 封装到UcenterMemberVo给远程调用类使用
     * @param memberId 用户id
     * @return
     */
    @PostMapping("/getMemberInfoById/{memberId}")
    public UcenterMemberVo getMemberInfoById(@PathVariable String memberId){
        UcenterMember byId = memberService.getById(memberId);
        UcenterMemberVo ucenterMemberVo = new UcenterMemberVo();
        BeanUtils.copyProperties(byId, ucenterMemberVo);
        return ucenterMemberVo;
    }
}

