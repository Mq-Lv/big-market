package com.mql.infrastructure.persistent.repository;

import com.mql.domain.strategy.model.entity.StrategyAwardEntity;
import com.mql.domain.strategy.model.entity.StrategyEntity;
import com.mql.domain.strategy.model.entity.strategyRuleEntity;
import com.mql.domain.strategy.repository.IStrategyRepository;
import com.mql.infrastructure.persistent.dao.StrategyAwardMapper;
import com.mql.infrastructure.persistent.dao.StrategyMapper;
import com.mql.infrastructure.persistent.dao.StrategyRuleMapper;
import com.mql.infrastructure.persistent.po.Strategy;
import com.mql.infrastructure.persistent.po.StrategyAward;
import com.mql.infrastructure.persistent.po.StrategyRule;
import com.mql.types.common.Constants;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ClassName: StrategyRepository
 * Package: com.mql.infrastructure.persistent.repository
 * Description: 策略仓储实现
 *
 * @Author lmq
 * @Create 2024/5/26 18:49
 * @Version 1.0
 */
@Repository
public class StrategyRepository implements IStrategyRepository {
    @Resource
    private StrategyAwardMapper strategyAwardMapper;

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private StrategyMapper strategyMapper;

    @Resource
    private StrategyRuleMapper strategyRuleMapper;
    /**
     * 查询指定策略下的奖品列表。
     * 该方法首先尝试从Redis缓存中获取奖品列表，如果缓存存在且不为空，则直接返回缓存数据；
     * 如果缓存不存在或为空，则从数据库中查询奖品信息，并将查询结果缓存到Redis中，最后返回查询结果。
     *
     * @param strategyId 策略的ID，用于查询该策略下的奖品列表。
     * @return 返回策略下的奖品实体列表。
     */
    @Override
    public List<StrategyAwardEntity> queryStrategyAwardList(Long strategyId) {
        // 构造缓存的key
        String cacheKey = Constants.RedisKey.STRATEGY_AWARD_KEY + strategyId;
        // 尝试从Redis缓存中获取奖品列表
        List<StrategyAwardEntity> strategyAwardEntities = redisTemplate.opsForList().range(cacheKey, 0, -1);

        // 如果缓存中存在数据且不为空，则直接返回缓存数据
        if(strategyAwardEntities != null && !strategyAwardEntities.isEmpty()){
            return strategyAwardEntities;
        }
        // 从数据库中查询奖品信息
        List<StrategyAward> strategyAwards = strategyAwardMapper.selectByStrategyId(strategyId);

        if(strategyAwards == null || strategyAwards.isEmpty()){
            return null;
        }
        // 构建奖品实体列表
        strategyAwardEntities = new ArrayList<>();
        // 组装奖品实体
        for(StrategyAward strategyAward: strategyAwards){
            StrategyAwardEntity strategyAwardEntity = new StrategyAwardEntity();
            strategyAwardEntity.setStrategyId(strategyAward.getStrategyId());
            strategyAwardEntity.setAwardId(strategyAward.getAwardId());
            strategyAwardEntity.setAwardCount(strategyAward.getAwardCount());
            strategyAwardEntity.setAwardCountSurplus(strategyAward.getAwardCountSurplus());
            strategyAwardEntity.setAwardRate(strategyAward.getAwardRate());
            strategyAwardEntities.add(strategyAwardEntity);
        }
        // 将查询结果缓存到Redis中
        redisTemplate.opsForList().leftPushAll(cacheKey, strategyAwardEntities);

        return strategyAwardEntities;
    }

    /**
     * 存储策略的奖品搜索映射表
     * @param key
     * @param rateRange
     * @param strategyAwardSearchMap
     */
    @Override
    public void storeStrategyAwardSearchMap(String key, int rateRange, Map<Integer, Integer> strategyAwardSearchMap) {
        String cacheKey = Constants.RedisKey.STRATEGY_RATE_TABLE_KEY + key;
        redisTemplate.opsForHash().putAll(cacheKey, strategyAwardSearchMap);
//        redisTemplate.boundHashOps(cacheKey).putAll(strategyAwardSearchMap);
        String cacheKeyRange = Constants.RedisKey.STRATEGY_RATE_RANGE_KEY + key;
//        redisTemplate.boundValueOps(cacheKeyRange).set(rateRange.intValue());
        redisTemplate.opsForValue().set(cacheKeyRange, rateRange);
    }

    /**
     * 获取策略的奖品搜索映射表的尺寸
     * @param strategyId
     * @return
     */
    @Override
    public Integer getRateRange(Long strategyId) {
        return getRateRange(String.valueOf(strategyId));
    }

    @Override
    public Integer getRateRange(String strategyId) {
        return (Integer) redisTemplate.boundValueOps(Constants.RedisKey.STRATEGY_RATE_RANGE_KEY + strategyId).get();
    }

    /**
     * 从策略映射表中获取指定随机值的奖品ID
     * @param strategyId
     * @param l
     * @return
     */
    @Override
    public Integer getStrategyAwardAssemble(String strategyId, long l) {
        return (Integer) redisTemplate.boundHashOps(Constants.RedisKey.STRATEGY_RATE_TABLE_KEY + strategyId).get(l);
    }

    /**
     * 基于策略ID查询策略实体，包含redis缓存
     * @param strategyId
     * @return
     */
    @Override
    public StrategyEntity queryStrategyEntityByStrategyId(Long strategyId) {
        String cacheKey = Constants.RedisKey.STRATEGY_KEY + strategyId;
        StrategyEntity strategyEntity = (StrategyEntity) redisTemplate.opsForValue().get(cacheKey);
        if(strategyEntity != null){
            return strategyEntity;
        }
        Strategy strategy =  strategyMapper.queryStrategyByStrategyId(strategyId);
        if(strategy == null){
            return null;
        }
        strategyEntity = new StrategyEntity();
        strategyEntity.setStrategyId(strategy.getStrategyId());
        strategyEntity.setStrategyDesc(strategy.getStrategyDesc());
        strategyEntity.setRuleModels(strategy.getRuleModels());
        redisTemplate.opsForValue().set(cacheKey, strategyEntity);

        return strategyEntity;
    }

    /**
     * 基于策略ID和规则模型查询策略规则实体（无redis缓存）
     * @param strategyId
     * @param ruleModel
     * @return
     */
    @Override
    public strategyRuleEntity queryStrategyRuleEntity(Long strategyId, String ruleModel) {
        StrategyRule strategyRule = strategyRuleMapper.queryStrategyRule(strategyId, ruleModel);
        if(strategyRule == null){
            return null;
        }
        strategyRuleEntity strategyRuleEntity = new strategyRuleEntity();
        strategyRuleEntity.setRuleDesc(strategyRule.getRuleDesc());
        strategyRuleEntity.setRuleModel(strategyRule.getRuleModel());
        strategyRuleEntity.setRuleValue(strategyRule.getRuleValue());
        strategyRuleEntity.setStrategyId(strategyRule.getStrategyId());
        strategyRuleEntity.setAwardId(strategyRule.getAwardId());
        strategyRuleEntity.setRuleType(strategyRule.getRuleType());
         return strategyRuleEntity;
    }
}
