package com.mql.infrastructure.persistent.dao;


import com.mql.infrastructure.persistent.po.Award;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 10744
* @description 针对表【award】的数据库操作Mapper
* @createDate 2024-05-25 21:37:39
* @Entity generator.pojo.Award
*/
@Mapper
public interface AwardMapper {

    int deleteByPrimaryKey(Long id);

    int insert(Award record);

    int insertSelective(Award record);

    Award selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Award record);

    int updateByPrimaryKey(Award record);

}
