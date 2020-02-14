package com.lpc.dao;

import com.lpc.mybatis.BaseDao;
import member.entity.mb_user;
import org.apache.ibatis.annotations.*;

import java.sql.Timestamp;

/**
 * @author Lin
 * @Date 2019/12/7
 */
@Mapper
public interface UserDao extends BaseDao {

    //UserDao继承了父接口BaseDao，当调用UserDao的方法时，如果UserDao中没有该方法，则会自动去寻找父接口中的方法

    /**
     * 第一次登录
     * 根据请求的密码和手机号 进入数据库查询
     * 以后登录直接用token+redis
     * @return
     */
    @Select("select * from mb_user where password = #{password}  and phone = #{phone}")
    mb_user getUserPhoneAndPsw(@Param("password") String password, @Param("phone") String phone);

    /**
     * 根据id获取用户信息
     * @param id
     * @return
     */
    @Select("select * from mb_user where id = #{id}")
    mb_user getUserById(@Param("id") Long id);

    /**
     * 根据获取到openID查询数据库中的用户信息
     * @param openID
     * @return
     */
    @Select("select * from mb_user where openID = #{openID}")
    mb_user getUserByLoginOpenID(@Param("openID") String openID);

    /**
     * 更新数据库 关联已有账户
     * 更新openID updateTime 通过id
     */
    @Update("update mb_user set openID = #{openID} ,updatedTime = #{updatedTime} where id = #{id}")
    void updateUserOpenID(@Param("openID") String openID, @Param("updatedTime") Timestamp updatedTime, @Param("id") Long id);

    /**
     * 修改密码
     */
    @Update("update mb_user set password = #{password},updatedTime = #{updatedTime} where phone = #{phone}")
    int updatePwd(mb_user mb_user);

    /**
     * 根据用户名查找用户数据
     */
    @Select("select * from mb_user where username = #{username}")
    mb_user selectUserByName(@Param("username") String username);

    /**
     * 根据手机号查找用户数据
     */
    @Select("select * from mb_user where phone = #{phone}")
    mb_user selectUserByPhone(@Param("phone") String phone);

    /**
     * 根据邮箱查找用户数据
     */
    @Select("select * from mb_user where email = #{email}")
    mb_user selectUserByEmail(@Param("email") String email);

    /**
     * 修改用户信息
     */
    @Update("update mb_user set username = #{username} ,email = #{email} ,phone = #{phone},address = #{address} where id = #{id}")
    int updateUser(mb_user mb_user);
}
