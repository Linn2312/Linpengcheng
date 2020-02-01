package com.lpc.dao;

import com.lpc.mybatis.BaseDao;
import commodity.entity.ItemDesc;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.repository.query.Param;

@Mapper
public interface ItemDescDao extends BaseDao {
	@Select(" SELECT * FROM tb_item_desc where id = #{id} ")
    ItemDesc getItemDesc(@Param("id") Long id);
}
