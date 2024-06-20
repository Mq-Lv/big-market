package com.mql.infrastructure.persistent.dao;


import com.mql.infrastructure.persistent.po.StrategyRule;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
* @author 10744
* @description 针对表【strategy_rule】的数据库操作Mapper
* @createDate 2024-05-25 17:06:01
* @Entity generator.pojo.StrategyRule
*/
@Mapper
public interface StrategyRuleMapper {

    int deleteByPrimaryKey(Long id);

    int insert(StrategyRule record);

    int insertSelective(StrategyRule record);

    StrategyRule selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(StrategyRule record);

    int updateByPrimaryKey(StrategyRule record);

    StrategyRule queryStrategyRule(@Param("strategyId") Long strategyId, @Param("ruleModel") String ruleModel);

    String queryStrategyRuleValue(@Param("strategyRule") StrategyRule strategyRule);
}
