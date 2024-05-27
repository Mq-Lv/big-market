package com.mql.test.infrastructure;

import com.alibaba.fastjson.JSON;
import com.mql.infrastructure.persistent.dao.AwardMapper;
import com.mql.infrastructure.persistent.po.Award;
import com.mql.types.common.Constants;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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

    @Resource
    private RedisTemplate redisTemplate;
    @Test
    /**
    * 测试mysql*/
    public void test1(){
        Award award = awardMapper.selectByPrimaryKey(1L);
        log.info("测试结果：{}", JSON.toJSONString(award));
    }

    @Test
    public void test4(){
        List<String> list = new ArrayList<>();
        list.add("dasdasd");
        list.add("dasdas6465d");
        list.add("dasdasd");
        list.add("dasdasd546");
        list.add("dasdas4654d");
        list.add("dasdasdsda");
//        redisTemplate.opsForList().leftPushAll("115", list);
//        List<String> list1 = redisTemplate.opsForList().range("115", 0, -1);
//        list1.forEach(System.out::println);
//        redisTemplate.boundListOps("110").rightPush(list);
        redisTemplate.boundListOps("122").leftPushAll(list);
        List range = redisTemplate.boundListOps("122").range(0, -1);
//        redisTemplate.opsForList().rightPush("113", list);
        int a = 0;

    }
    @Test
    /**
     * 测试redis
     */
    public void test(){
        redisTemplate.opsForHash().put("strategy_id_100001", "1", "101");
        redisTemplate.opsForHash().put("strategy_id_100001", "2", "101");
        redisTemplate.opsForHash().put("strategy_id_100001", "3", "101");
        redisTemplate.opsForHash().put("strategy_id_100001", "4", "102");
        redisTemplate.opsForHash().put("strategy_id_100001", "5", "102");
        redisTemplate.opsForHash().put("strategy_id_100001", "6", "103");
        redisTemplate.opsForHash().put("strategy_id_100001", "7", "104");
        redisTemplate.delete("strategy_id_100001");
        redisTemplate.delete(Constants.RedisKey.STRATEGY_AWARD_KEY + 100001L);



        log.info("测试结果：{}",redisTemplate.opsForHash().get("strategy_id_100001","7"));

    }

    @Test
    public void test2(){
        BigDecimal bigDecimal = new BigDecimal("0.01");
        log.info("测试结果：{}",bigDecimal);
    }

}
