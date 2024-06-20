package com.mql.domain.strategy.service.raffle;

import com.mql.domain.strategy.model.entity.RaffleAwardEntity;

import com.mql.domain.strategy.model.entity.RaffleFactorEntity;
import com.mql.domain.strategy.model.entity.RuleActionEntity;
import com.mql.domain.strategy.model.entity.StrategyEntity;
import com.mql.domain.strategy.repository.IStrategyRepository;
import com.mql.domain.strategy.service.IRaffleStrategy;
import com.mql.domain.strategy.service.armory.IStrategyDispatch;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * ClassName: AbstractRaffleStrategy
 * Package: com.mql.domain.strategy.service.raffle
 * Description: 抽奖策略抽象类
 *
 * @Author lmq
 * @Create 2024/5/27 16:59
 * @Version 1.0
 */
@Slf4j
public abstract class AbstractRaffleStrategy implements IRaffleStrategy {
    protected IStrategyRepository repository;

    protected IStrategyDispatch dispatch;

    public AbstractRaffleStrategy(IStrategyRepository repository, IStrategyDispatch dispatch) {
        this.repository = repository;
        this.dispatch = dispatch;
    }

    @Override
    public RaffleAwardEntity performRaffle(RaffleFactorEntity raffleFactorEntity) {
        // 参数校验
        if (raffleFactorEntity == null || raffleFactorEntity.getStrategyId() == null) {
            log.error("抽奖参数异常");
            return null;
        }
        String userId = raffleFactorEntity.getUserId();
        Long strategyId = raffleFactorEntity.getStrategyId();
        if (StringUtils.isBlank(userId) || strategyId == null) {
            log.error("抽奖参数异常");
            return null;
        }

        // 查询策略
        StrategyEntity strategyEntity = repository.queryStrategyEntityByStrategyId(strategyId);

        RuleActionEntity<RuleActionEntity.RaffleBeforeEntity> ruleActionEntity = doCheckRaffleBeforeLogic(RaffleFactorEntity.builder().userId(userId).strategyId(strategyId).build(), strategyEntity.getRuleModels());




        return null;
    }

    protected abstract RuleActionEntity<RuleActionEntity.RaffleBeforeEntity> doCheckRaffleBeforeLogic(RaffleFactorEntity raffleFactorEntity, String... logics);

}
