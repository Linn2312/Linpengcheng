package com.lpc.dao;

import commodity.entity.Item;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author Lin
 * @Date 2020/2/2
 */
public interface ItemRepository extends ElasticsearchRepository<Item,String> {
}
