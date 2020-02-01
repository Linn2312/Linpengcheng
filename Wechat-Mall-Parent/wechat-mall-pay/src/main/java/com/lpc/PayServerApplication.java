package com.lpc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author Lin
 * @Date 2019/12/30
 */
@SpringBootApplication
@EnableEurekaClient
public class PayServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(PayServerApplication.class,args);
    }
}
