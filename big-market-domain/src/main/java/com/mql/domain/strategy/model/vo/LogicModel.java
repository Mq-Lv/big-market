package com.mql.domain.strategy.model.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * ClassName: LogicModel
 * Package: com.mql.domain.strategy.model.vo
 * Description:
 *
 * @Author lmq
 * @Create 2024/5/28 10:48
 * @Version 1.0
 */
@Getter
@AllArgsConstructor
public enum LogicModel {
    RULE_WIGHT("rule_weight","【抽奖前规则】根据抽奖权重返回可抽奖范围KEY"),
    RULE_BLACKLIST("rule_blcaklist","【抽奖前规则】黑名单规则过滤，命中黑名单则直接返回"),
    ;
    private final String code;
    private final String info;
}
