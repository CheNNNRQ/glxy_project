package com.diodi.eduService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author diodi
 * @create 2021-08-02-21:13
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.diodi"})
public class EduApplication {
    public static void main(String[] args) {
        SpringApplication.run( EduApplication.class,args);
        System.out.println("******************************服务启动成功******************************");
    }
}
