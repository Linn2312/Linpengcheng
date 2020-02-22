package com.lpc.feign;

import com.lpc.responseConfig.BaseResponseService;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author Lin
 * @Date 2020/1/29
 */
@Component
public class CartHystrixFallBack extends BaseResponseService implements CartFeign{

    @Override
    public Map<String, Object> selectCart(String userId) {
        return setResultError("系统正忙，请稍后再试");
    }

    @Override
    public Map<String, Object> addCart(String userId, String itemId) {
        return setResultError("系统正忙，请稍后再试");
    }

    @Override
    public Map<String, Object> delCart(String userId, String itemId) {
        return setResultError("系统正忙，请稍后再试");
    }

    @Override
    public Integer selectUserCartCount(Long id) {
        return null;
    }
}
