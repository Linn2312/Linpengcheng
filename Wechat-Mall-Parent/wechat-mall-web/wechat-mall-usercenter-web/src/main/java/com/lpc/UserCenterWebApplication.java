package com.lpc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author Lin
 * @Date 2020/2/22
 */
@SpringBootApplication
@EnableFeignClients
@EnableHystrix
@EnableEurekaClient
public class UserCenterWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserCenterWebApplication.class,args);
    }
}
