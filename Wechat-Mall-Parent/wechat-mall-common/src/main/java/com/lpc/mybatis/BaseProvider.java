package com.lpc.mybatis;

import com.lpc.utils.ReflectionUtils;
import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

/**
 * @author Lin
 * @Date 2019/12/7
 *
 * 自定义封装拼接sql语句的工具类
 */
public class BaseProvider {
    /**
     * 拼接insert语句
     */
    public String save(Map<String, Object> map){
        //实体类
        Object oj = map.get("oj");
        //表名
        String tableName = (String) map.get("tableName");
        //开始拼接
        SQL sql = new SQL(){{
            INSERT_INTO(tableName);
            //VALUES(全部的属性，全部的值)
            VALUES(ReflectionUtils.getInsertFields(oj),ReflectionUtils.getInsertFieldsValue(oj));
        }};
        return sql.toString();
    }
}
