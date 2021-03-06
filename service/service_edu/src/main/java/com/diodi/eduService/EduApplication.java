package com.diodi.eduService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author diodi
 * @create 2021-08-02-21:13
 */
@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient //服务发现功能

@ComponentScan(basePackages = {"com.diodi"})
public class EduApplication {
    public static void main(String[] args) {
        SpringApplication.run( EduApplication.class,args);
        System.out.println("******************************服务启动成功******************************");
    }
}
