package com.mql.domain.strategy.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ClassName: RuleNatterEntity
 * Package: com.mql.domain.strategy.model.entity
 * Description:
 *
 * @Author lmq
 * @Create 2024/5/27 17:02
 * @Version 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RuleMatterEntity {

    private String userId;

    private Long strategyId;

    private Integer awardId;

    private String ruleModel;
}
