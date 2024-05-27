package com.mql.infrastructure.persistent.dao;


import com.mql.infrastructure.persistent.po.StrategyAward;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author 10744
* @description 针对表【strategy_award】的数据库操作Mapper
* @createDate 2024-05-25 17:06:01
* @Entity generator.pojo.StrategyAward
*/
@Mapper
public interface StrategyAwardMapper {

    int deleteByPrimaryKey(Long id);

    int insert(StrategyAward record);

    int insertSelective(StrategyAward record);

    StrategyAward selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(StrategyAward record);

    int updateByPrimaryKey(StrategyAward record);

    List<StrategyAward> selectByStrategyId(Long strategyId);
}
