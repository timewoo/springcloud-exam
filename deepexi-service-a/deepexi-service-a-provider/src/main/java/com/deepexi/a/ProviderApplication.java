package com.deepexi.a;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Created by donh on 2018/11/5.
 * EnableDiscoveryClient 用于启动服务发现功能
 * EnableFeignClients 用于启动Fegin功能
 */
@EnableEurekaClient
@EnableFeignClients
@SpringBootApplication
public class ProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProviderApplication.class, args);
    }
}