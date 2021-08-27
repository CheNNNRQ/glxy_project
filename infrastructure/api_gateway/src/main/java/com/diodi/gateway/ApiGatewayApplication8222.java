package com.diodi.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author diodi
 * @create 2021-08-26-18:54
 */
@SpringBootApplication
@EnableDiscoveryClient
public class ApiGatewayApplication8222 {
    public static void main(String[] args) {
        SpringApplication.run( ApiGatewayApplication8222.class,args);
        System.out.println("******************************服务启动成功******************************");
    }
}
