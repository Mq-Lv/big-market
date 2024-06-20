package com.mql.domain.strategy.service.raffle;

import com.mql.domain.strategy.model.entity.RaffleFactorEntity;
import com.mql.domain.strategy.model.entity.RuleActionEntity;
import com.mql.domain.strategy.model.entity.RuleMatterEntity;
import com.mql.domain.strategy.model.vo.LogicModel;
import com.mql.domain.strategy.model.vo.RuleLogicCheckTypeVo;
import com.mql.domain.strategy.repository.IStrategyRepository;
import com.mql.domain.strategy.service.armory.IStrategyDispatch;
import com.mql.domain.strategy.service.rule.impl.RuleBackListLogicFilter;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * ClassName: DefaultRaffleStrategy
 * Package: com.mql.domain.strategy.service.raffle
 * Description:
 *
 * @Author lmq
 * @Create 2024/5/29 11:07
 * @Version 1.0
 */
public class DefaultRaffleStrategy extends AbstractRaffleStrategy{

    private IStrategyRepository repository;
    private IStrategyDispatch dispatch;

    @Resource
    private RuleBackListLogicFilter ruleBackListLogicFilter;



    public DefaultRaffleStrategy(IStrategyRepository repository, IStrategyDispatch dispatch) {
        super(repository, dispatch);
    }

    @Override
    protected RuleActionEntity<RuleActionEntity.RaffleBeforeEntity> doCheckRaffleBeforeLogic(RaffleFactorEntity raffleFactorEntity, String... logics) {
        // 检查是否配置黑名单
        String ruleBackList = Arrays.stream(logics).filter(s -> s.equals(LogicModel.RULE_BLACKLIST.getCode())).findFirst().orElse(null);

        if (ruleBackList != null) {
            RuleMatterEntity ruleMatterEntity = new RuleMatterEntity();
            ruleMatterEntity.setRuleModel(LogicModel.RULE_BLACKLIST.getCode());
            ruleMatterEntity.setStrategyId(raffleFactorEntity.getStrategyId());
            ruleMatterEntity.setUserId(raffleFactorEntity.getUserId());
            RuleActionEntity<RuleActionEntity.RaffleBeforeEntity> ruleActionEntity = ruleBackListLogicFilter.filter(ruleMatterEntity);
            // 是否存在于黑名单
            if (!ruleActionEntity.getCode().equals(RuleLogicCheckTypeVo.ALLOW.getCode())) {
                return ruleActionEntity;
            }
        }
        return null;
    }
}
