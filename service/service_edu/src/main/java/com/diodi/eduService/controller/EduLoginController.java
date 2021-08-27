package com.diodi.eduService.controller;

import com.diodi.commonutils.R;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

/**
 * 模拟登陆
 * @author diodi
 * @create 2021-08-03-21:46
 */
@Api(tags = "登录")
@RestController
@RequestMapping("/eduService/user")
public class EduLoginController {
    //login
    @PostMapping("/login")
    public R login(){
        return R.ok().data("token", "admin");
    }

    //info
    @GetMapping("/info")
    public R info(){
        return R.ok().data("roles","admin").data("name","diodi").data("avatar","https://diodi-college.oss-cn-beijing.aliyuncs.com/2021/08/06/e2c52039d4144bfdb90af5a425e3e0636.gif");
    }
}
