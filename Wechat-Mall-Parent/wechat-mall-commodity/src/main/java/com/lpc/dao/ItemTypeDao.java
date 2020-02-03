package com.lpc.dao;

import commodity.entity.ItemType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ItemTypeDao {
	@Select("select * from tb_item_type limit 0,3")
    List<ItemType> getTwoItemsType();

    @Select("select * from tb_item_type")
    List<ItemType> getShowMoreItemsType();

    @Select("select * from tb_item_type where id = 5")
    List<ItemType> getPartType();
}
