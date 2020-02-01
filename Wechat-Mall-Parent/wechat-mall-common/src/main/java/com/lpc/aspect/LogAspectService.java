package com.lpc.aspect;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * @author Lin
 * @Date 2019/12/6
 */
@Component
@Aspect
@Slf4j
public class LogAspectService {
    private JSONObject jsonObject = new JSONObject();

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void serviceAspect(){}

    @Before("serviceAspect()")
    public void methodBefore(JoinPoint joinPoint){
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes)RequestContextHolder
                .getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        log.info("===================请求内容====================");
        try{
            //打印请求内容
            log.info("########请求地址:" + request.getRequestURL().toString());
            log.info("########请求方式:" + request.getMethod());
            log.info("########请求类方法:" + joinPoint.getSignature());
            log.info("########请求类方法参数:" + Arrays.toString(joinPoint.getArgs()));
        }catch(Exception e){
            log.error("#########LogAspectService.class methodBefore() ###error:" + e);
        }
        log.info("====================请求内容===================");
    }

    //在方法执行成功后打印返回的内容
    @AfterReturning(returning = "o",value = "serviceAspect()")
    public void methodAfterReturning(Object o){
        log.info("====================返回内容===================");
        try{
            log.info("#########Response内容:" + jsonObject.toJSONString(o));
        }catch (Exception e){
            log.error("#########LogAspectService.class methodAfterReturning() ###error:" + e);
        }
        log.info("====================返回内容==================");
    }
}
