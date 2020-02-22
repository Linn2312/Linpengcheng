package com.lpc.feign;

import member.service.api.UserService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author Lin
 * @Date 2019/12/11
 */
@FeignClient(name = "member-server",fallback = UserHystrixFallBack.class,path = "/member")
public interface UserFeign extends UserService {

    /**
     * 原本建立的feign客户端接口内容需要和被调用的接口内容一致。那么现在只需要继承该接口即可
     */
}
