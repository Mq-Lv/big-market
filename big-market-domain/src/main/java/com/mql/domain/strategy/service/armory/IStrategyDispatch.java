package com.mql.domain.strategy.service.armory;

/**
 * ClassName: IStrategyDispatch
 * Package: com.mql.domain.strategy.service.armory
 * Description: 策略抽奖调度
 *
 * @Author lmq
 * @Create 2024/5/27 13:17
 * @Version 1.0
 */
public interface IStrategyDispatch {
    /**
     * 获取抽奖策略装配的随机结果
     * @param strategyId
     * @return
     */
    Integer getRandomAwardId(Long strategyId);

    /**
     * 考虑规则权重，获取抽奖策略装配的随机结果
     * @param strategyId
     * @param ruleWeight
     * @return
     */
    Integer getRandomAwardId(Long strategyId, Integer ruleWeight);

}
