package blossom.project.springbootkp.mqproducer;


import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@SpringBootTest
class MQProducerApplicationTests {
    @Autowired
    private RocketMQTemplate rocketMQTemplate;
    /**
     * 发送一个带有msgId的消息
     */
    @Test
    void keyProducer() {
        String msgId = UUID.randomUUID().toString();
        Message<String> message = MessageBuilder.withPayload("我是一个带有key的消息")
                .setHeader(RocketMQHeaders.KEYS, msgId)
                .build();
        rocketMQTemplate.syncSend("bootTestTopic", message);
    }
}
