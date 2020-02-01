package com.lpc.dao;

import com.lpc.mybatis.BaseDao;
import order.entity.OrderInfo;
import org.apache.ibatis.annotations.*;

import java.sql.Timestamp;
import java.util.List;


/**
 * @author Lin
 * @Date 2020/1/15
 */
@Mapper
public interface OrderDao extends BaseDao {

    @Update("update order_info set status = #{status},updatedTime = #{updatedTime} where id = #{id}")
    void updateOrderStatus(@Param("id") Long id, @Param("status") Byte status, @Param("updatedTime") Timestamp updatedTime);

    @Insert("insert into order_info(id,userId,amount,status,createdTime,updatedTime) values (null,#{userId},#{amount},#{status},#{createdTime},#{updatedTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id") // useGeneratedKeys 设置为"true"表明要 MyBatis 获取由数据库自动生成的主键；keyProperty="id"指定把获取到的主键值注入到 XXX（实体类） 的 id 属性
    void saveOrderInfo(OrderInfo orderInfo);

    @Delete("delete from order_info where id = #{orderInfoId}")
    void delOrder(@Param("orderInfoId") Long orderInfoId);

    @Select("select * from order_info where userId = #{userId}")
    List<OrderInfo> selectUserAllOrder(@Param("userId") Long userId);

    @Delete("delete from order_detail where orderId = #{orderId}")
    void delOrderDetail(@Param("orderId") Long orderId);

    @Select("select b.itemId from order_info a inner join order_detail b on a.id = b.orderId where a.id = #{orderId}")
    List<Long> getItemsIdByOrderId(@Param("orderId") Long orderId);

    @Select("select b.shoppingnum from order_info a inner join order_detail b on a.id = b.orderId where a.id = #{orderId}")
    List<Long> getShoppingnum(@Param("orderId") Long orderId);

    @Select("select count(*) from order_info where userId = #{userId}")
    Integer selectUserOrderCount(@Param("userId") Long id);

    @Select("select status from order_info where id = #{orderId}")
    Byte getOrderStatus(@Param("orderId") Long orderId);

    @Select("select * from order_info")
    List<OrderInfo> selectAllOrders();
}
