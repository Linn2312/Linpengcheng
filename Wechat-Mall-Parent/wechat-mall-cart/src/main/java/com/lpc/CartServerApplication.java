package com.lpc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author Lin
 * @Date 2020/1/5
 */
@SpringBootApplication
@EnableEurekaClient
public class CartServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(CartServerApplication.class,args);
    }
}
