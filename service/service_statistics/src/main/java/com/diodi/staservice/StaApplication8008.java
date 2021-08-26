package com.diodi.staservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author diodi
 * @create 2021-08-25-10:25
 */
@SpringBootApplication
@EnableDiscoveryClient //服务发现功能
@EnableFeignClients
@EnableScheduling //自动化开启
@MapperScan("com.diodi.staservice.mapper")
@ComponentScan(basePackages = {"com.diodi"})
public class StaApplication8008 {
    public static void main(String[] args) {
        SpringApplication.run( StaApplication8008.class,args);
        System.out.println("******************************服务启动成功******************************");
    }
}
