package com.lpc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author Lin
 * @Date 2019/12/11
 */
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@EnableScheduling
@EnableHystrix
public class MobileWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(MobileWebApplication.class,args);
    }
}
