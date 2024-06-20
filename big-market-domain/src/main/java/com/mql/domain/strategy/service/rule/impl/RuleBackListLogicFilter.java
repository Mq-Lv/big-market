package com.mql.domain.strategy.service.rule.impl;

import com.mql.domain.strategy.model.entity.RuleActionEntity;
import com.mql.domain.strategy.model.entity.RuleMatterEntity;
import com.mql.domain.strategy.model.vo.LogicModel;
import com.mql.domain.strategy.model.vo.RuleLogicCheckTypeVo;
import com.mql.domain.strategy.repository.IStrategyRepository;
import com.mql.domain.strategy.service.rule.ILogicFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * ClassName: RuleBackListLogicFilter
 * Package: com.mql.domain.strategy.service.rule.impl
 * Description: 基于用户id，策略id以及规则模型 判断 黑名单过滤
 *
 * @Author lmq
 * @Create 2024/5/28 10:09
 * @Version 1.0
 */
@Slf4j
@Component
public class RuleBackListLogicFilter implements ILogicFilter<RuleActionEntity.RaffleBeforeEntity> {

    @Resource
    private IStrategyRepository repository;
    @Override
    public RuleActionEntity<RuleActionEntity.RaffleBeforeEntity> filter(RuleMatterEntity ruleMatterEntity) {
        log.info("规则过滤-黑名单 userId:{} strategyId:{} ruleModel:{}", ruleMatterEntity.getUserId(), ruleMatterEntity.getStrategyId(), ruleMatterEntity.getRuleModel());
        String userId = ruleMatterEntity.getUserId();
        String ruleValue = repository.queryStrategyRuleValue(ruleMatterEntity.getStrategyId(), ruleMatterEntity.getAwardId(), ruleMatterEntity.getRuleModel());

        String[] splitRuleValue = ruleValue.split(":");
        Integer awardId = Integer.valueOf(splitRuleValue[0]);
        String[] userBlackIds = splitRuleValue[1].split(",");
        for (String userBlackId : userBlackIds) {
            if (userId.equals(userBlackId)) {
                RuleActionEntity build = RuleActionEntity.<RuleActionEntity.RaffleBeforeEntity>builder()
                        .code(RuleLogicCheckTypeVo.TAKE_OVER.getCode())
                        .info(RuleLogicCheckTypeVo.TAKE_OVER.getInfo())
                        .ruleModel(LogicModel.RULE_BLACKLIST.getCode())
                        .data(RuleActionEntity.RaffleBeforeEntity.builder().strategyId(ruleMatterEntity.getStrategyId()).awardId(awardId).build())
                        .build();
                return build;
            }
        }
        RuleActionEntity build = RuleActionEntity.builder()
                .code(RuleLogicCheckTypeVo.ALLOW.getCode())
                .info(RuleLogicCheckTypeVo.ALLOW.getInfo())
                .build();

        return build;
    }
}
