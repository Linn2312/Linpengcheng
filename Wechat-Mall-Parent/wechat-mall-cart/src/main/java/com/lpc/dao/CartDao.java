package com.lpc.dao;

import com.lpc.mybatis.BaseDao;
import commodity.entity.Item;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author Lin
 * @Date 2020/1/5
 */
@Mapper
public interface CartDao extends BaseDao {
    @Select("select a.* from `wechat-shop-commodity`.tb_item a inner join `wechat-shop-cart`.cart b on a.id = b.itemId where b.userId = #{userId}")
    List<Item> selectCart(@Param("userId") String userId);

    @Delete("delete from cart where userId = #{userId} and itemId = #{itemId}")
    void delCart(@Param("userId") String userId, @Param("itemId") String itemId);

    @Delete("delete from cart where userId = #{userId}")
    void emptyCart(@Param("userId") String userId);

    @Select("select count(*) from cart where userId = #{userId}")
    Integer selectUserCartCount(@Param("userId") Long id);
}
