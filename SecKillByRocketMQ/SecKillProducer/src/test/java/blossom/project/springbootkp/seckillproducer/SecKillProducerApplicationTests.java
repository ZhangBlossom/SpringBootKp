package blossom.project.springbootkp.seckillproducer;

import blossom.project.springbootkp.seckillproducer.entity.MsgModel;
import com.alibaba.fastjson.JSONObject;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
class SecKillProducerApplicationTests {

    private List<MsgModel> msgModels = Arrays.asList(
            new MsgModel("qwer", 1L, "下单"),
            new MsgModel("qwer", 1L, "短信"),
            new MsgModel("qwer", 1L, "物流"),

            new MsgModel("zxcv", 2L, "下单"),
            new MsgModel("zxcv", 2L, "短信"),
            new MsgModel("zxcv", 2L, "物流")
    );

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Test
    void syncProducer() {
        for (int i = 0; i < 10; i++) {
            rocketMQTemplate.syncSend("broadcastTopic", "使用springboot集成rocketmq" + i);
        }
    }

    @Test
    void asyncProducer() {
        rocketMQTemplate.asyncSend("bootTestTopic", "发送一条异步消息", new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                System.out.println("发送成功");
            }

            @Override
            public void onException(Throwable throwable) {
                System.out.println("发送失败" + throwable.getMessage());
            }
        });
    }

    @Test
    void oneWayProducer() {
        rocketMQTemplate.sendOneWay("bootTestTopic", "发送一个单向消息");
    }

    @Test
    void delayProducer() {
        Message<String> message = MessageBuilder.withPayload("这是一条延迟消息").build();
        rocketMQTemplate.syncSend("bootTestTopic", message, 3000, 2);
    }

    /**
     * 有序消息 那么我们发送的消息需要指定吧消息放到哪一个队列去
     */
    @Test
    void orderedProducer() {
        msgModels.forEach(x -> {
            String s = JSONObject.toJSONString(x);
            rocketMQTemplate.syncSendOrderly("orderlyTopic", s, x.getOrderSn());
        });
    }

    @Test
    void tagProducer() {
        rocketMQTemplate.syncSend("bootTestTopic:tagA", "我是一个带标签的消息");
    }

    @Test
    void keyProducer() {
        Message<String> message = MessageBuilder.withPayload("我是一个带有key的消息").setHeader(RocketMQHeaders.KEYS, "testKey")
                .build();
        rocketMQTemplate.syncSend("bootTestTopic", message);
    }
}
