package com.mql.domain.strategy.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ClassName: RaddleFactorEntity
 * Package: com.mql.domain.strategy.model.entity
 * Description: 抽奖实体
 *
 * @Author lmq
 * @Create 2024/5/27 16:49
 * @Version 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RaffleFactorEntity {
    private String userId;
    private Long strategyId;
}
