package com.mql.domain.strategy.model.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.PriorityQueue;

/**
 * ClassName: RuleLogicCheckTypeVo
 * Package: com.mql.domain.strategy.model.entity.vo
 * Description:
 *
 * @Author lmq
 * @Create 2024/5/27 17:13
 * @Version 1.0
 */
@Getter
@AllArgsConstructor
public enum RuleLogicCheckTypeVo {
    ALLOW("0000","放行；执行后续流程，不受规则引擎影响"),
    TAKE_OVER("0001","接管；后续的流程，受规则引擎执行结果影响"),
    ;

    private final String code;
    private final String info;
}
