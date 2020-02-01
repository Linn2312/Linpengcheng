package com.lpc.eureka8100;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author Lin
 * @Date 2020/1/15
 */
@SpringBootApplication
@EnableEurekaServer
public class Eureka8100Application {
    public static void main(String[] args) {
        SpringApplication.run(Eureka8100Application.class,args);
    }

}
