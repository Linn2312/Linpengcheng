package com.lpc.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Timestamp;

/**
 * @author Lin
 * @Date 2019/12/7
 *
 * 反射工具类：拼接属性字段和属性值
 * 属性拼接和属性值拼接
 * 通过反射机制获取实体类中的属性和属性值，用于进行sql语句的拼接(父entity和子entity的实体要拼接到一起)
 */
public class ReflectionUtils {

    /**
     * 根据属性，获取get方法
     * @param ob 对象
     * @param name 属性名
     */
    public static Object getGetMethod(Object ob , String name){
        Method[] m = ob.getClass().getMethods();
        for (Method method : m) {
            if (("get" + name).toLowerCase().equals(method.getName().toLowerCase())) {
                try {
                    return method.invoke(ob);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * insert语句
     *
     * 拼接实体属性的总方法
     * 将mb_common这个父实体的属性也拼接起来
     * @param oj 传入的实体类
     */
    public static String getInsertFields(Object oj){
        Class clazz = oj.getClass();
        String objectEntity = declaredFields(clazz.getDeclaredFields());
        String commonEntity= declaredFields(clazz.getSuperclass().getDeclaredFields());
        return objectEntity + "," + commonEntity ;
    }

    /**
     * insert语句
     *
     * 拼接实体属性值的总方法
     * @param oj 传入的实体类
     */
    public static String getInsertFieldsValue(Object oj){
        Class clazz = oj.getClass();
        String objectEntityValue = declaredFieldsValue(oj,clazz.getDeclaredFields());
        String commonEntityValue= declaredFieldsValue(oj,clazz.getSuperclass().getDeclaredFields());
        return objectEntityValue + "," + commonEntityValue;
    }

    /**
     * 因为要获取父实体类mb_common的属性和子实体类的属性,将获取实体属性的方法代码抽取出来
     * @param declaredFields 实体类的属性集合
     */
    private static String declaredFields(Field[] declaredFields){
        StringBuilder stringBuffer = new StringBuilder();
        for (int i = 0; i < declaredFields.length; i++) {
            stringBuffer.append(declaredFields[i].getName());
            if (i < declaredFields.length - 1){
                stringBuffer.append(",");
            }
        }
        return stringBuffer.toString();
    }

    /**
     * 因为要获取父实体类mb_common的属性值和子实体类的属性值,将获取实体属性值的方法代码抽取出来
     * @param oj 传入的子实体类
     * @param declaredFields 实体类的属性集合
     */
    private static String declaredFieldsValue(Object oj, Field[] declaredFields){
        StringBuilder stringBuffer = new StringBuilder();
        for (int i = 0; i < declaredFields.length; i++) {
            try {
                //单个属性
                Field field = declaredFields[i];
                //设置允许操作私有属性
                field.setAccessible(true);
                //单个属性的值
                Object value = field.get(oj);
                //标识符
                boolean flag = false;
                //给String类型和TimeStamp类型的属性值添加单引号 的逻辑代码
                if ((value instanceof String || value instanceof Timestamp)){
                    flag = true;
                }
                if (flag){
                    stringBuffer.append("'");
                    stringBuffer.append(value);
                    stringBuffer.append("'");
                }else {
                    stringBuffer.append(value);
                }
                if (i < declaredFields.length - 1){
                    stringBuffer.append(",");
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return stringBuffer.toString();
    }
}
