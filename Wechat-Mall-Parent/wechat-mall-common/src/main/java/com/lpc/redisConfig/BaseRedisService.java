package com.lpc.redisConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author Lin
 * @Date 2019/12/5
 *
 * 封装redis工具类  可用于的数据结构：List Set Map String
 * 在此只是封装了String类型
 */
@Component
public class BaseRedisService {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    //自定义封装Set数据结构
    private void set_Set(String key, Object value, Long timeOut){
        if (value != null){
            if (value instanceof String){
                String s_value = (String)value;
                this.stringRedisTemplate.opsForSet().add(key,s_value);
            }
            //设置有效期
            if (timeOut != null){
                //TimeUnit是java.util.concurrent包下面的一个类，表示给定单元粒度的时间段
                this.stringRedisTemplate.expire(key,timeOut, TimeUnit.SECONDS); //表示以秒为单位
            }
        }
    }

    //设置Set类型参数 无超时时间
    public void setSet(String key, Object value){
        this.set_Set(key,value,null);
    }
    //设置Set类型参数 有超时时间
    public void setSet(String key, Object value, Long timeOut){
        this.set_Set(key,value,timeOut);
    }

    //根据key获取 值为set类型
    public Set getSet(String key){
        return this.stringRedisTemplate.opsForSet().members(key);
    }

    //根据key批量删除元素
    public void delSet(String key, Object...o){
        if (o != null){
            this.stringRedisTemplate.opsForSet().remove(key,o);
        }
    }


    //自定义封装String数据结构
    private void set_String(String key, Object value, Long timeOut){
        if (value != null){
            if (value instanceof String){
                String s_value = (String)value;
                this.stringRedisTemplate.opsForValue().set(key,s_value);
            }
            //设置有效期
            if (timeOut != null){
                //TimeUnit是java.util.concurrent包下面的一个类，表示给定单元粒度的时间段
                this.stringRedisTemplate.expire(key,timeOut, TimeUnit.SECONDS); //表示以秒为单位
            }
        }
    }

    //设置String类型参数 无超时时间
    public void setString(String key, Object value){
        this.set_String(key,value,null);
    }
    //设置String类型参数 有超时时间
    public void setString(String key, Object value, Long timeOut){
        this.set_String(key,value,timeOut);
    }

    //根据key获取 值为String类型
    public String getString(String key){
        return this.stringRedisTemplate.opsForValue().get(key);
    }

    //根据key删除元素
    public void delString(String key){
        this.stringRedisTemplate.delete(key);
    }

}
