package com.mql.infrastructure.persistent.po;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName strategy
 */
@Data
public class Strategy implements Serializable {
    /**
     * 自增ID
     */
    private Long id;

    /**
     * 抽奖策略ID
     */
    private Long strategyId;

    /**
     * 抽奖策略描述
     */
    private String strategyDesc;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    private static final long serialVersionUID = 1L;
}