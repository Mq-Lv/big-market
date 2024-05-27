package com.mql.test.domain;

import com.mql.domain.strategy.service.armory.IStrategyArmory;
import com.mql.domain.strategy.service.armory.IStrategyDispatch;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.SpringBootDependencyInjectionTestExecutionListener;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * ClassName: StrategyArmoryTest
 * Package: com.mql.test.domain
 * Description:
 *
 * @Author lmq
 * @Create 2024/5/26 20:52
 * @Version 1.0
 */
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class )
public class StrategyArmoryTest {
    @Resource
    private IStrategyArmory strategyArmory;

    @Resource
    private IStrategyDispatch strategyArmory1;

    @Test
    public void test_strategy_armory() {
        strategyArmory.assembleLotteryStrategy(100001L);
    }

    @Test
    public void test_strategy_armory_get_random_award_id() {
        Integer randomAwardId = strategyArmory1.getRandomAwardId(100001L);
        log.info("randomAwardId: {}", randomAwardId);
        randomAwardId = strategyArmory1.getRandomAwardId(100001L);
        log.info("randomAwardId: {}", randomAwardId);
        randomAwardId = strategyArmory1.getRandomAwardId(100001L);
        log.info("randomAwardId: {}", randomAwardId);

    }
    @Test
    public void test_strategy_armory_get_random_weight_award_id() {
        Integer randomAwardId = strategyArmory1.getRandomAwardId(100001L,Integer.parseInt("4000"));
        log.info("randomAwardId: {}", randomAwardId);
        randomAwardId = strategyArmory1.getRandomAwardId(100001L,Integer.parseInt("5000"));
        log.info("randomAwardId: {}", randomAwardId);
        randomAwardId = strategyArmory1.getRandomAwardId(100001L,Integer.parseInt("6000"));
        log.info("randomAwardId: {}", randomAwardId);

    }

}
