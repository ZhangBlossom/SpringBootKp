package zhang.blossom.seckillbyrocketmq;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import zhang.blossom.seckillbyrocketmq.constant.MQConstant;
import zhang.blossom.seckillbyrocketmq.mapper.MessageIdempotentMapper;

import java.util.List;
import java.util.UUID;

/**
 * @author: 张锦标
 * @date: 2023/8/17 14:29
 * RepeatRocketMQTest类
 */
@SpringBootTest
public class RepeatRocketMQTest {
    @Autowired
    private MessageIdempotentMapper mapper;
    //发送一个重复消息
    @Test
    public void repeatProducer() throws MQClientException, MQBrokerException, RemotingException, InterruptedException {
        DefaultMQProducer producer = new DefaultMQProducer("test_producer_group");
        producer.setNamesrvAddr(MQConstant.NAMESRV);
        producer.start();
        String key = UUID.randomUUID().toString();
        Message message1 = new Message("testTopic",null,key,"扣减库存-1 ".getBytes());
        Message message2 = new Message("testTopic",null,key,"扣减库存-1 ".getBytes());
        producer.send(message1);
        producer.send(message2);
        System.out.println("发送成功,key:"+key);
        producer.shutdown();
    }
    @Test
    public void repeatConsumer() throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("test_producer_group");
        consumer.setNamesrvAddr(MQConstant.NAMESRV);
        consumer.subscribe("testTopic","*");
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                return null;
            }
        });
    }
}
