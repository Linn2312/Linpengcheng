package com.lpc.feign.user;

import com.lpc.responseConfig.BaseResponseService;
import member.entity.mb_user;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author Lin
 * @Date 2020/1/29
 *
 * hystrix断路保护机制 ,如果该服务down掉了，会自动降级，执行以下对应方法
 */
@Component
public class UserHystrixFallBack extends BaseResponseService implements UserFeign{
    @Override
    public Map<String, Object> register(mb_user mb_user) {
        return setResultError("系统正忙，请稍后再试");
    }

    @Override
    public Map<String, Object> login(mb_user mb_user) {
        return setResultError("系统正忙，请稍后再试");
    }

    @Override
    public Map<String, Object> getUserInfo(String token) {
        return setResultError("系统正忙，请稍后再试");
    }

    @Override
    public Map<String, Object> getUserByLoginOpenID(String openID) {
        return setResultError("系统正忙，请稍后再试");
    }

    @Override
    public Map<String, Object> findPassword(String email) {
        return setResultError("系统正忙，请稍后再试");
    }

    @Override
    public Map<String, Object> updatePwd(String phone, String password) {
        return setResultError("系统正忙，请稍后再试");
    }

    @Override
    public Map<String, Object> updateUser(mb_user mb_user) {
        return setResultError("系统正忙，请稍后再试");
    }
}
