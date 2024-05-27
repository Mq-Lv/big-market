package com.mql.domain.strategy.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * ClassName: StrategyEntity
 * Package: com.mql.domain.strategy.model.entity
 * Description: 策略实体
 *
 * @Author lmq
 * @Create 2024/5/27 13:54
 * @Version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StrategyEntity {

    /**
     * 抽奖策略ID
     */
    private Long strategyId;

    /**
     * 抽奖策略描述
     */
    private String strategyDesc;

    /**
     * 规则模型，rule配置的模型同步到此表，便于使用
     */
    private String ruleModels;

    public String[] ruleModels(){
        if(ruleModels == null || ruleModels.length() == 0) return null;
        return ruleModels.split(",");
    }

    public boolean getRuleWeight(){
        for (String s : ruleModels()) {
            if(s.equals("rule_weight")){
                return true;
            }
        }
        return false;
    }


}
