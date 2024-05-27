package com.mql.domain.strategy.repository;

import com.mql.domain.strategy.model.entity.StrategyAwardEntity;
import com.mql.domain.strategy.model.entity.StrategyEntity;
import com.mql.domain.strategy.model.entity.strategyRuleEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * ClassName: IStrategyRepository
 * Package: com.mql.domain.strategy.repository
 * Description: 策略仓储接口
 *
 * @Author lmq
 * @Create 2024/5/26 18:47
 * @Version 1.0
 */
public interface IStrategyRepository {
    /**
     * 查询策略奖品列表
     * @param strategyId
     * @return
     */
    List<StrategyAwardEntity> queryStrategyAwardList(Long strategyId);

    /**
     * 存储策略奖品搜索Map
     * @param key
     * @param rateRange
     * @param strategyAwardSearchMap
     */
    void storeStrategyAwardSearchMap(String key, int rateRange, Map<Integer, Integer> strategyAwardSearchMap);

    /**
     * 获取总的奖品编号值
     * @param strategyId
     * @return
     */
    Integer getRateRange(Long strategyId);

    Integer getRateRange(String strategyId);

    /**
     * 根据策略ID和随机值奖品编号获取奖品ID
     * @param strategyId
     * @param l
     * @return
     */
    Integer getStrategyAwardAssemble(String strategyId, long l);

    /**
     * 根据策略ID获取策略实体
     * @param strategyId
     * @return
     */
    StrategyEntity queryStrategyEntityByStrategyId(Long strategyId);

    /**
     * 根据策略ID和ruleModel获取模型
     * @param strategyId
     * @param ruleModel
     * @return
     */
    strategyRuleEntity queryStrategyRuleEntity(Long strategyId, String ruleModel);
}
