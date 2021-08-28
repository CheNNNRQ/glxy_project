package com.diodi.aclservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author diodi
 * @create 2021-08-27-10:59
 */
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan("com.diodi")
@MapperScan("com.diodi.aclservice.mapper")
public class AclApplication8009 {
    public static void main(String[] args) {
        SpringApplication.run( AclApplication8009.class,args);
        System.out.println("******************************服务启动成功******************************");
    }
}
