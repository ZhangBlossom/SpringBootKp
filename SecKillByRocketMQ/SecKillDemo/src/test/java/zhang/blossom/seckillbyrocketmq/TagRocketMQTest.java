package zhang.blossom.seckillbyrocketmq;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.*;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import zhang.blossom.seckillbyrocketmq.constant.MQConstant;

import java.io.IOException;
import java.util.List;

/**
 * @author: 张锦标
 * @date: 2023/8/17 11:18
 * TagRocketMQTest类
 */
@SpringBootTest
public class TagRocketMQTest {
    @Test
    public void tagProducer() throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer("test-producer-group");
        producer.setNamesrvAddr(MQConstant.NAMESRV);
        producer.start();
        Message message1 = new Message("testTopic", "test1","test1的消息".getBytes() );
        Message message2 = new Message("testTopic", "test2","test2的消息".getBytes() );
        producer.send(message1);
        producer.send(message2);
        producer.shutdown();
        System.out.println("消息发送成功");
    }

    @Test
    public void test1Consumer() throws MQClientException, IOException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("test-producer-group");
        consumer.setNamesrvAddr(MQConstant.NAMESRV);
        consumer.subscribe("testTopic", "test1");
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list,
                                                            ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                System.out.println("消费test1的消息"+new String(list.get(0).getBody()));
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        consumer.start();
        System.in.read();
    }
    @Test
    public void test2Consumer() throws MQClientException, IOException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("test-producer-group");
        consumer.setNamesrvAddr(MQConstant.NAMESRV);
        consumer.subscribe("testTopic", "test1 || test2");
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list,
                                                            ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                System.out.println("消费test1/test2的消息"+new String(list.get(0).getBody()));
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        consumer.start();
        System.in.read();
    }
}
