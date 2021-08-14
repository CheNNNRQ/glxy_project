package com.diodi.vodservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author diodi
 * @create 2021-08-13-19:14
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@ComponentScan(basePackages = {"com.diodi"})
public class VodApplicationMain8003 {
    public static void main(String[] args) {
        SpringApplication.run( VodApplicationMain8003.class,args);
        System.out.println("******************************服务启动成功******************************");
    }
}
