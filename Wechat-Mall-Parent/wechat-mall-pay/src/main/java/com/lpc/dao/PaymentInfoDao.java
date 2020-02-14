package com.lpc.dao;

import com.lpc.mybatis.BaseDao;
import order.entity.OrderDetail;
import order.entity.OrderInfo;
import org.apache.ibatis.annotations.*;
import org.springframework.data.repository.query.Param;
import pay.entity.PaymentInfo;

import java.util.List;


@Mapper
public interface PaymentInfoDao extends BaseDao {

	@Select("select * from payment_info where id=#{id}")
    PaymentInfo getPaymentInfo(@Param("id") Long id);

	@Insert("insert into payment_info (id,typeid,orderid,plantformorderid,price,state,createdTime,updatedTime) value(null,#{typeId},#{orderId},#{plantformorderId},#{price},#{state},#{createdTime},#{updatedTime})")
	@Options(useGeneratedKeys = true, keyProperty = "id") // useGeneratedKeys 设置为"true"表明要 MyBatis 获取由数据库自动生成的主键；keyProperty="id"指定把获取到的主键值注入到 XXX（实体类） 的 id 属性
    void savePaymentInfo(PaymentInfo paymentInfo);

	@Select("select * from payment_info where orderid=#{orderId}")
    PaymentInfo getPayInfoByOrderId(@Param("orderId") String orderId);

	@Update("update payment_info set state =#{state},paymessage=#{payMessage},plantformorderId=#{plantformorderId},updatedTime=#{updatedTime} where orderid=#{orderId} ")
	void updatePayInfo(PaymentInfo paymentInfo);
}
