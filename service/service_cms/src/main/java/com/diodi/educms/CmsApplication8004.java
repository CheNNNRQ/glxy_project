package com.diodi.educms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author diodi
 * @create 2021-08-15-21:25
 */
@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient //服务发现功能
@ComponentScan({"com.diodi"})
@MapperScan("com.diodi.educms.mapper")
public class CmsApplication8004 {
    public static void main(String[] args) {
        SpringApplication.run( CmsApplication8004.class,args);
        System.out.println("******************************服务启动成功******************************");
    }
}
