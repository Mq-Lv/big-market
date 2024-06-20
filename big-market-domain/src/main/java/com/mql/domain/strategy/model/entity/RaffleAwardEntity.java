package com.mql.domain.strategy.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ClassName: RaffleAwardEntity
 * Package: com.mql.domain.strategy.model.entity
 * Description:
 *
 * @Author lmq
 * @Create 2024/5/28 10:37
 * @Version 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RaffleAwardEntity {
    private Integer awardId;

    private String awardTitle;

    private String awardConfig;

    private Integer sort;
}
