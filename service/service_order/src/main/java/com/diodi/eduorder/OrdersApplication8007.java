package com.diodi.eduorder;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author diodi
 * @create 2021-08-23-11:14
 */
@SpringBootApplication
@MapperScan("com.diodi.eduorder.mapper")
@EnableFeignClients
@ComponentScan(basePackages = {"com.diodi"})
@EnableDiscoveryClient
public class OrdersApplication8007 {
    public static void main(String[] args) {
        SpringApplication.run( OrdersApplication8007.class,args);
        System.out.println("******************************服务启动成功******************************");
    }
}
