package com.mql.domain.strategy.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName: strategyRuleEntity
 * Package: com.mql.domain.strategy.model.entity
 * Description:
 *
 * @Author lmq
 * @Create 2024/5/27 14:37
 * @Version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class strategyRuleEntity {

    /**
     * 抽奖策略ID
     */
    private Long strategyId;

    /**
     * 抽奖奖品ID【规则类型为策略，则不需要奖品ID】
     */
    private Integer awardId;

    /**
     * 抽象规则类型；1-策略规则、2-奖品规则
     */
    private Integer ruleType;

    /**
     * 抽奖规则类型【rule_random - 随机值计算、rule_lock - 抽奖几次后解锁、rule_luck_award - 幸运奖(兜底奖品)】
     */
    private String ruleModel;

    /**
     * 抽奖规则比值
     */
    private String ruleValue;

    /**
     * 抽奖规则描述
     */
    private String ruleDesc;


    /**
     * 获取rule_weight模型中ruleValue的值
     * @return
     */
    public Map<String, List<Integer>> getRuleWeightValues(){
        if (!ruleModel.equals("rule_weight")) return null;
        Map<String, List<Integer>> ruleWeightValues = new HashMap<>();
        String[] ruleValueGroups = ruleValue.split(" ");
        for (String ruleValueGroup : ruleValueGroups) {
            String[] ruleValueGroupItems = ruleValueGroup.split(":");
            String[] ruleValues = ruleValueGroupItems[1].split(",");
            List<Integer> ruleValueList = new ArrayList<>();
            for (String ruleValue : ruleValues){
                ruleValueList.add(Integer.valueOf(ruleValue));
            }
            ruleWeightValues.put(ruleValueGroupItems[0], ruleValueList);
        }

        return ruleWeightValues;
    }
}
