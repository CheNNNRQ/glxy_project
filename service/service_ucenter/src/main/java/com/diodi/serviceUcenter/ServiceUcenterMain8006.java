package com.diodi.serviceUcenter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author diodi
 * @create 2021-08-18-13:41
 */
@SpringBootApplication
@ComponentScan("com.diodi")
@EnableDiscoveryClient //服务发现功能
@MapperScan("com.diodi.serviceUcenter.mapper")
public class ServiceUcenterMain8006 {
    public static void main(String[] args) {
        SpringApplication.run(ServiceUcenterMain8006.class, args);
        System.out.println("******************************服务启动成功******************************");
    }
}
