package com.mql.infrastructure.persistent.dao;


import com.mql.infrastructure.persistent.po.Strategy;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 10744
* @description 针对表【strategy】的数据库操作Mapper
* @createDate 2024-05-25 17:06:01
* @Entity generator.pojo.Strategy
*/
@Mapper
public interface StrategyMapper {

    int deleteByPrimaryKey(Long id);

    int insert(Strategy record);

    int insertSelective(Strategy record);

    Strategy selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Strategy record);

    int updateByPrimaryKey(Strategy record);

}
