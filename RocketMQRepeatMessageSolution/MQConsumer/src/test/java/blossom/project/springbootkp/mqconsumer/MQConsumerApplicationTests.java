package blossom.project.springbootkp.mqconsumer;

import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MQConsumerApplicationTests {
    @Autowired
    private RocketMQTemplate rocketMQTemplate;
    @Test
    void consumer() {

    }

}
