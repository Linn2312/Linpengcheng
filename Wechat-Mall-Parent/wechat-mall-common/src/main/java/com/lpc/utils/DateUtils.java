package com.lpc.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Lin
 * @Date 2019/12/7
 * 日期工具类
 */
@Slf4j
public class DateUtils{

    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private static final String[] FORMATS = {"yyyy-MM-dd", "yyyy-MM-dd HH:mm",
            "yyyy-MM-dd HH:mm:ss", "HH:mm", "HH:mm:ss", "yyyy-MM",
            "yyyy-MM-dd HH:mm:ss.S"};

    private DateUtils() {

    }

    /**
     * 获取当前系统时间
     */
    public static Timestamp getTimeStamp(){
        return new Timestamp(new Date().getTime());
    }

    /**
     * 将Timestamp类型转为字符串 再转为long 返回毫秒数
     */
    public static long fromDateStringToLong(String inVal) { // 此方法计算时间毫秒
        Date date = null; // 定义时间类型
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
            date = inputFormat.parse(inVal); // 将字符型转换成日期型
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date.getTime(); // 返回毫秒数
    }

    /**
     * 字符串转换成日期 主要解决json传入日期问题
     */
    public static Timestamp convert(String str) {
        if (str != null && str.length() > 0) {
            if (str.length() > 10 && str.charAt(10) == 'T') {
                str = str.replace('T', ' ');// 去掉json-lib加的T字母
            }
            for (String format : FORMATS) {
                if (format.length() == str.length()) {
                    try {
                        Date date = new SimpleDateFormat(format).parse(str);
                        return (Timestamp) date;
                    } catch (ParseException e) {
                        if (log.isWarnEnabled()) {
                            log.warn(e.getMessage());
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * 字符串、日期格式 转换日期
     * @param format 例如: "yyyy-MM-dd HH:mm:ss"
     * @param str 例如: "2012-12-03 23:21:24"
     */
    public static Timestamp convert(String str, String format) {
        if (!StringUtils.isEmpty(str)) {
            try {
                Date date = new SimpleDateFormat(format).parse(str);
                return (Timestamp) date;
            } catch (ParseException e) {
                if (log.isWarnEnabled()) {
                    log.warn(e.getMessage());
                }
            }
        }
        return null;
    }

    /**
     * 将日期转换成format字符串
     * @param date 例如: Sun Jun 10 09:18:00 CST 2018
     * @param dateFormat 例如: "yyyy-MM-dd HH:mm:ss"
     */
    public static String convert(Date date, String dateFormat) {
        if (date == null) {
            return null;
        }
        if (null == dateFormat) {
            dateFormat = DATE_TIME_FORMAT;
        }
        return new SimpleDateFormat(dateFormat).format(date);
    }

    /**
     * 根据传入的日期  转换成这样格式的字符串 如：“yyyy-MM-dd HH:mm:ss”
     */
    public static String convert(Date date) {
        return convert(date, DATE_TIME_FORMAT);
    }

    /**
     * 时间拼接 将日期和实现拼接 ymd 如2012-05-15 hm 如0812 最终 2012-05-15 08:12:00
     */
    public static Timestamp concat(String ymd, String hm) {
        if (!StringUtils.isBlank(ymd) && !StringUtils.isBlank(hm)) {
            try {
                String dateString = ymd.concat(" ").concat(hm.substring(0, 2)).concat(":").concat(hm.substring(2, 4)).concat(":00");
                Date date = DateUtils.convert(dateString, DateUtils.DATE_TIME_FORMAT);
                return (Timestamp) date;
            } catch (NullPointerException e) {
                if (log.isWarnEnabled()) {
                    log.warn(e.getMessage());
                }
            }
        }
        return null;
    }

    /**
     * 将传入的时间格式的字符串转成时间对象      例如：传入2012-12-03 23:21:24
     */
    public static Timestamp strToDate(String dateStr) {
        SimpleDateFormat formatDate = new SimpleDateFormat(DATE_TIME_FORMAT);
        Date date = null;
        try {
            date = formatDate.parse(dateStr);
        } catch (Exception e) {

        }
        return (Timestamp) date;
    }
}
