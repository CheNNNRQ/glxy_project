package com.diodi.eduService.controller;

import com.diodi.commonutils.R;
import org.springframework.web.bind.annotation.*;

/**
 * 模拟登陆
 * @author diodi
 * @create 2021-08-03-21:46
 */
@RestController
@CrossOrigin
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
        return R.ok().data("roles","admin").data("name","diodi").data("avatar","https://diodi-college.oss-cn-beijing.aliyuncs.com/2021/08/05/c8121e46cd2c41dc9ee10781236c8d7e1F875832E0096EEA8D184A139F74EAF8.jpg");
    }
}
