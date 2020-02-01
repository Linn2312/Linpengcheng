package com.lpc.dao;

import commodity.entity.Item;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * @author Lin
 * @Date 2020/2/1
 */
public interface ESDao extends ElasticsearchRepository<Item,String> {
    List<Item> findByName(String keywords);
}
