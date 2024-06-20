package com.mql.domain.strategy.model.entity;

import com.mql.domain.strategy.model.vo.RuleLogicCheckTypeVo;

import lombok.*;

/**
 * ClassName: RuleActionEntity
 * Package: com.mql.domain.strategy.model.entity
 * Description: 规则动作实体
 *
 * @Author lmq
 * @Create 2024/5/27 17:07
 * @Version 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RuleActionEntity<T extends RuleActionEntity.RaffleEntity>{

    private String code = RuleLogicCheckTypeVo.ALLOW.getCode();
    private String info = RuleLogicCheckTypeVo.ALLOW.getInfo();
    private String ruleModel;
    private T data;

    static public class RaffleEntity{


    }
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @EqualsAndHashCode(callSuper = true)
    // 抽奖前
    static public class RaffleBeforeEntity extends RaffleEntity{
        private Long strategyId;
        private  String ruleWeightValueKey;
        private Integer awardId;
    }

    // 抽奖后

    static public class RaffleAfterEntity extends RaffleEntity{

    }

}
