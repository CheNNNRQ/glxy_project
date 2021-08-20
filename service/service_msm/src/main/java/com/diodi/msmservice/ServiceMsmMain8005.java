package com.diodi.msmservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author diodi
 * @create 2021-08-17-14:40
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@ComponentScan("com.diodi")
public class ServiceMsmMain8005 {
    public static void main(String[] args) {
        SpringApplication.run( ServiceMsmMain8005.class,args);
        System.out.println("******************************服务启动成功******************************");
    }
}
