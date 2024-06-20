package com.mql.domain.strategy.service.rule;

import com.mql.domain.strategy.model.entity.RuleActionEntity;
import com.mql.domain.strategy.model.entity.RuleMatterEntity;

/**
 * ClassName: IlogicFilter
 * Package: com.mql.domain.strategy.service.rule
 * Description: 抽奖规则过滤接口
 *
 * @Author lmq
 * @Create 2024/5/27 17:05
 * @Version 1.0
 */
public interface ILogicFilter<T extends RuleActionEntity.RaffleEntity> {
    RuleActionEntity<T> filter(RuleMatterEntity ruleMatterEntity);
}
