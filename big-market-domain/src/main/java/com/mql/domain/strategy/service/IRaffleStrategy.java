package com.mql.domain.strategy.service;


import com.mql.domain.strategy.model.entity.RaffleAwardEntity;
import com.mql.domain.strategy.model.entity.RaffleFactorEntity;

/**
 * ClassName: IRaffleStrategy
 * Package: com.mql.domain.strategy.service.rule
 * Description: 抽奖策略接口
 *
 * @Author lmq
 * @Create 2024/5/27 16:41
 * @Version 1.0
 */

public interface IRaffleStrategy {
    RaffleAwardEntity performRaffle(RaffleFactorEntity raffleFactorEntity);
}
