package com.lpc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author Lin
 * @Date 2019/12/10
 */
@SpringBootApplication
@EnableEurekaClient
public class MessageServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(MessageServerApplication.class,args);
    }
}
