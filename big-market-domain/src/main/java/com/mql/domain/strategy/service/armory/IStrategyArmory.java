package com.mql.domain.strategy.service.armory;

/**
 * ClassName: IStrategyArmory
 * Package: com.mql.domain.strategy.service.armory
 * Description:负责初始化策略计算
 *
 * @Author lmq
 * @Create 2024/5/26 18:42
 * @Version 1.0
 */
public interface IStrategyArmory {

    /**
     * 进行策略装配（基于策略ID，装配抽奖模型即数据）,包含不同权重策略的抽奖模型
     * @param strategyId
     */
    void assembleLotteryStrategy(Long strategyId);


}
