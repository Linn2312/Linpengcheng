package com.lpc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author Lin
 * @Date 2019/12/5
 */
@SpringBootApplication
@EnableEurekaClient
public class MemberServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(MemberServerApplication.class,args);
    }
}
