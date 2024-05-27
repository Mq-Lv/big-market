package com.mql.domain.strategy.service.armory;

import com.mql.domain.strategy.model.entity.*;

import com.mql.domain.strategy.repository.IStrategyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * ClassName: StrategyArmory
 * Package: com.mql.domain.strategy.service.armory
 * Description: 负责初始化策略
 *
 * @Author lmq
 * @Create 2024/5/26 18:45
 * @Version 1.0
 */
@Slf4j
@Service
public class StrategyArmory implements IStrategyArmory, IStrategyDispatch{
    @Resource
    private IStrategyRepository repository;
    /**
     * 组装抽奖策略。（包含所有权重模型）
     * 根据策略ID查询策略配置，并根据配置计算出每个奖品对应的抽奖编号，最后打乱顺序并存储与redis。
     *
     * @param strategyId 抽奖策略的ID，用于查询具体的抽奖配置。
     */
    @Override
    public void assembleLotteryStrategy(Long strategyId) {
        // 查询策略配置
        List<StrategyAwardEntity> strategyAwardEntities = repository.queryStrategyAwardList(strategyId);
        if(strategyAwardEntities == null){
            log.info("未查询到策略ID:{}", strategyId);
            return;
        }
        // 初始策略配置装配（未考虑任何策略规则模型）
        assembleLotteryStrategy(String.valueOf(strategyId), strategyAwardEntities);

        // 权重策略配置 - 适用于配置了rule weight 权重规则的配置
        StrategyEntity strategyEntity = repository.queryStrategyEntityByStrategyId(strategyId);
        if(strategyEntity == null){
            log.info("未查询到策略ID:{}", strategyId);
            return;
        }
        if(strategyEntity.getRuleWeight()){
            strategyRuleEntity strategyRuleEntity = repository.queryStrategyRuleEntity(strategyId, "rule_weight");
            if(strategyRuleEntity == null){
                log.info("未查询到策略ID:{}，对应的rule_weight的模型", strategyId);
                return;
            }
            Map<String, List<Integer>> ruleWeightValues = strategyRuleEntity.getRuleWeightValues();
            if(ruleWeightValues == null){
                log.info("未查询到策略ID:{}，对应的rule_weight的模型的值", strategyId);
                return;
            }
            ruleWeightValues.keySet().forEach(key -> {
                List<Integer> awardIdList = ruleWeightValues.get(key);
                List<StrategyAwardEntity> strategyAwardEntitiesClone = new ArrayList<>(strategyAwardEntities);
                strategyAwardEntitiesClone.removeIf(strategyAwardEntity -> !awardIdList.contains(strategyAwardEntity.getAwardId()));
               assembleLotteryStrategy(strategyId + "_" + key, strategyAwardEntitiesClone);
            });
        }
    }

    /**
     * 组装抽奖策略（针对每种权重模型）
     * 根据策略ID查询策略配置，并根据配置计算出每个奖品对应的抽奖编号，最后打乱顺序并存储与redis。
     * @param key
     * @param strategyAwardEntities
     */

    public void assembleLotteryStrategy(String key, List<StrategyAwardEntity> strategyAwardEntities){
        //获取最小概率
        BigDecimal minAwardRate = strategyAwardEntities.stream().map(StrategyAwardEntity::getAwardRate).min(BigDecimal::compareTo).orElse(BigDecimal.ZERO);

        // 概率总和
        BigDecimal totalAwardRate = strategyAwardEntities.stream().map(StrategyAwardEntity::getAwardRate).reduce(BigDecimal.ZERO, BigDecimal::add);

        // 获取总的范围值
        BigDecimal rateRange = totalAwardRate.divide(minAwardRate, 0, RoundingMode.CEILING);

        // 获取各个奖品对应的抽奖编号
        ArrayList<Integer> strategyAwardList = new ArrayList<>();
        for(StrategyAwardEntity strategyAwardEntity : strategyAwardEntities){
            Integer awardId = strategyAwardEntity.getAwardId();
            BigDecimal awardRate = strategyAwardEntity.getAwardRate();
            for(int i = 1; i <= awardRate.multiply(rateRange).setScale(0, RoundingMode.CEILING).intValue(); i++){
                strategyAwardList.add(awardId);
            }
        }
        // 打乱奖品编号顺序
        Collections.shuffle(strategyAwardList);

        // 与奖品进行对应
        Map<Integer,Integer> strategyAwardSearchMap = new HashMap<>();
        for(int i = 0; i < strategyAwardList.size(); i++){
            strategyAwardSearchMap.put(i, strategyAwardList.get(i));
        }
        // 存储于redis中
        repository.storeStrategyAwardSearchMap(key, strategyAwardList.size(), strategyAwardSearchMap);
    }

    /**
     * 基于策略ID获取随机商品
     * @param strategyId
     * @return
     */
    @Override
    public Integer getRandomAwardId(Long strategyId) {
        Integer rateRange = repository.getRateRange(strategyId);
        long random = ThreadLocalRandom.current().nextInt(rateRange);
//        log.info("random:{}", random);
        return repository.getStrategyAwardAssemble(String.valueOf(strategyId), random);
    }

    /**
     * 基于策略ID和权重模型获取随机商品
     * @param strategyId
     * @param ruleWeight
     * @return
     */
    @Override
    public Integer getRandomAwardId(Long strategyId, Integer ruleWeight) {
        String key = strategyId + "_" + ruleWeight;
        Integer rateRange = repository.getRateRange(key);
        long random = ThreadLocalRandom.current().nextInt(rateRange);
//        log.info("random:{}", random);
        return repository.getStrategyAwardAssemble(key, random);
    }

}
