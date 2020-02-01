package com.lpc.feign.item;

import com.lpc.responseConfig.BaseResponseService;
import commodity.entity.Item;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * @author Lin
 * @Date 2020/1/29
 */
@Component
public class ItemHystrixFallBack extends BaseResponseService implements ItemFeign,ItemDescFeign{

    @Override
    public Map<String, Object> getItemDesc(Long id) {
        return setResultError("系统正忙，请稍后再试");
    }

    @Override
    public Map<String, Object> getItems() {
        return setResultError("系统正忙，请稍后再试");
    }

    @Override
    public Map<String, Object> getAllItems() {
        return setResultError("系统正忙，请稍后再试");
    }

    @Override
    public Map<String, Object> getItem(Long id) {
        return setResultError("系统正忙，请稍后再试");
    }

    @Override
    public List<Item> selectItemsByIds(List<Long> ids) {
        return null;
    }

    @Override
    public Map<String, Object> getAllParts() {
        return setResultError("系统正忙，请稍后再试");
    }

    @Override
    public Map<String, Object> search(String keywords) {
        return setResultError("系统正忙，请稍后再试");
    }
}
