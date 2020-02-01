package com.lpc.dao;

import com.lpc.mybatis.BaseDao;
import commodity.entity.Item;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Mapper
public interface ItemDao extends BaseDao {

	/**
	 * 级联查询 根据商品类目id级联查询对应的商品信息
	 * @param id
	 * @return
	 */
	@Select("select a.* from tb_item as a inner join tb_item_type as b on a.type_id = b.id where b.id = #{id}")
    List<Item> getAllItems(@Param("id") Long id);

	/**
	 * 根据商品id查询商品信息
	 * @param id
	 * @return
	 */
	@Select("select * from tb_item where id = #{id}")
    Item getItem(@Param("id") Long id);
}
