package blossom.project.springbootkp.seckillservice;

import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SecKillConsumerApplicationTests {
    @Autowired
    private RocketMQTemplate rocketMQTemplate;
    @Test
    void consumer() {

    }

}
