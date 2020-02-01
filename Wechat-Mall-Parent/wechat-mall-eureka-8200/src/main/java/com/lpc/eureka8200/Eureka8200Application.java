package com.lpc.eureka8200;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author Lin
 * @Date 2020/1/15
 */
@SpringBootApplication
@EnableEurekaServer
public class Eureka8200Application {
    public static void main(String[] args) {
        SpringApplication.run(Eureka8200Application.class,args);
    }

}
