package zuul82;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * @author Lin
 * @Date 2020/1/27
 */
@SpringBootApplication
@EnableEurekaClient
@EnableZuulProxy        //开启zuul代理
public class ZuulServer82Application {
    public static void main(String[] args) {
        SpringApplication.run(ZuulServer82Application.class,args);
    }
}
