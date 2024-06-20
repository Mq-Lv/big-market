package com.mql.domain.strategy.service.rule.impl;

import com.mql.domain.strategy.model.entity.RuleActionEntity;
import com.mql.domain.strategy.model.entity.RuleMatterEntity;
import com.mql.domain.strategy.model.vo.LogicModel;
import com.mql.domain.strategy.model.vo.RuleLogicCheckTypeVo;
import com.mql.domain.strategy.repository.IStrategyRepository;
import com.mql.domain.strategy.service.rule.ILogicFilter;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.rule.Rule;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName: RuleWeightLogicFilter
 * Package: com.mql.domain.strategy.service.rule.impl
 * Description: 基于用户id，策略id以及规则模型 判断 加权过滤
 *
 * @Author lmq
 * @Create 2024/5/28 11:12
 * @Version 1.0
 */
@Slf4j
@Component
public class RuleWeightLogicFilter implements ILogicFilter<RuleActionEntity.RaffleBeforeEntity> {
    @Resource
    private IStrategyRepository repository;

    private Long userScore = 4500L;
    @Override
    public RuleActionEntity<RuleActionEntity.RaffleBeforeEntity> filter(RuleMatterEntity ruleMatterEntity) {
        log.info("规则过滤-权重范围 userId:{} strategyId:{} ruleModel:{}", ruleMatterEntity.getUserId(), ruleMatterEntity.getStrategyId(), ruleMatterEntity.getRuleModel());
        String userId = ruleMatterEntity.getUserId();
        String ruleValue = repository.queryStrategyRuleValue(ruleMatterEntity.getStrategyId(), ruleMatterEntity.getAwardId(), ruleMatterEntity.getRuleModel());

        // 获取策略Id对应的权重规则
        Map<Long, String> analyticalValue = getAnalyticalValue(ruleValue);
        if(analyticalValue == null || analyticalValue.isEmpty()){
            return RuleActionEntity.<RuleActionEntity.RaffleBeforeEntity>builder().code(RuleLogicCheckTypeVo.ALLOW.getCode())
                    .info(RuleLogicCheckTypeVo.ALLOW.getInfo()).build();
        }
        List<Long> analyticalSortedKeys = new ArrayList<>(analyticalValue.keySet());
        analyticalSortedKeys.sort((o1, o2) -> o1.compareTo(o2));

        // 找出最小的符合的权重值
        Long nextValue = null;
        for(int i = 0, j = analyticalSortedKeys.size() - 1; i <= j;){
            int mid = (i + j) / 2;
            if(analyticalSortedKeys.get(mid).equals(userScore)){
                nextValue = analyticalSortedKeys.get(mid);
                break;
            }else if(analyticalSortedKeys.get(mid) > userScore){
                j = mid - 1;
            } else if (analyticalSortedKeys.get(mid) < userScore) {
                if(mid + 1 >= analyticalSortedKeys.size() || analyticalSortedKeys.get(mid + 1) > userScore ){
                    nextValue = analyticalSortedKeys.get(mid);
                    break;
                }
                i = mid + 1;
            }
        }

        if(nextValue != null){
            return RuleActionEntity.<RuleActionEntity.RaffleBeforeEntity>builder()
                    .code(RuleLogicCheckTypeVo.TAKE_OVER.getCode())
                    .info(RuleLogicCheckTypeVo.TAKE_OVER.getInfo())
                    .ruleModel(LogicModel.RULE_WIGHT.getCode())
                    .data(RuleActionEntity.RaffleBeforeEntity.builder()
                            .strategyId(ruleMatterEntity.getStrategyId())
                            .ruleWeightValueKey(analyticalValue.get(nextValue)).build()).build();
        }

        return RuleActionEntity.<RuleActionEntity.RaffleBeforeEntity>builder().code(RuleLogicCheckTypeVo.ALLOW.getCode())
                .info(RuleLogicCheckTypeVo.ALLOW.getInfo()).build();
    }

    /**
     * 分解权重，获取权重key 4000 以及其对应的权重规格 4000：102，103，104
     * @param ruleValue
     * @return
     */
    private Map<Long, String> getAnalyticalValue(String ruleValue) {
        Map<Long, String> ruleValuesMap = new HashMap<>();
        String[] ruleValueGroups = ruleValue.split(" ");
        for (String ruleValueGroup : ruleValueGroups) {
            if(ruleValueGroup == null || ruleValueGroup.length() == 0){
                return ruleValuesMap;
            }
            String[] ruleValueGroupItems = ruleValueGroup.split(":");

            ruleValuesMap.put(Long.valueOf(ruleValueGroupItems[0]), ruleValueGroup);
        }
        return ruleValuesMap;
    }
}
