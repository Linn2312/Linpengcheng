package com.lpc.entity;

import lombok.Data;

import java.sql.Timestamp;

/**
 * @author Lin
 * @Date 2019/12/6
 *
 * 表中相同的字段和属性
 *
 */
@Data
public class BaseEntity {

    /**
     * 主键
     */
    private Long id;

    /**
     * 创建时间
     */
    private Timestamp createdTime;

    /**
     * 修改时间
     */
    private Timestamp updatedTime;
}
