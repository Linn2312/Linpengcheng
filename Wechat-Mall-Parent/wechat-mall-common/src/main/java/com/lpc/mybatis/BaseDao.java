package com.lpc.mybatis;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;

/**
 * @author Lin
 * @Date 2019/12/7
 *
 * 封装对数据库的增改查。删除就不封装了。一般公司都用的是逻辑删除，不使用物理删除。定义个deleteFlag做标记即可
 */
public interface BaseDao {
    /**
     * insert语句
     * @param oj 实体类
     * @param tableName 表名
     * insert into 表名(列名) values(值);
     */
    //@InsertProvider注解:可自定义sql语句拼接，type:提供拼接方法的类，method:类中的哪一个方法
    @InsertProvider(type = BaseProvider.class,method = "save")
    void save(@Param("oj") Object oj, @Param("tableName") String tableName);

}
