package com.lpc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author Lin
 * @Date 2020/2/10
 */
@SpringBootApplication
@EnableHystrix
@EnableFeignClients
public class PayWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(PayWebApplication.class,args);
    }
}
