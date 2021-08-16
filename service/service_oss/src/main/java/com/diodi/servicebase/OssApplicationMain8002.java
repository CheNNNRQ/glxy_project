package com.diodi.servicebase;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author diodi
 * @create 2021-08-05-15:16
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableDiscoveryClient //服务发现功能
@ComponentScan(basePackages = {"com.diodi"})
public class OssApplicationMain8002 {
    public static void main(String[] args) {
        SpringApplication.run( OssApplicationMain8002.class,args);
        System.out.println("******************************服务启动成功******************************");
    }
}
