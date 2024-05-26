package com.mql.test.infrastructure;

import com.alibaba.fastjson.JSON;
import com.mql.infrastructure.persistent.dao.AwardMapper;
import com.mql.infrastructure.persistent.po.Award;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * ClassName: DaoTest
 * Package: com.mql.test.infrastructure
 * Description:Dao层测试
 *
 * @Author lmq
 * @Create 2024/5/25 22:04
 * @Version 1.0
 */
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class DaoTest {

    @Resource
    private AwardMapper awardMapper;
    @Test
    public void test1(){
        Award award = awardMapper.selectByPrimaryKey(1L);
        log.info("测试结果：{}", JSON.toJSONString(award));
    }

}
