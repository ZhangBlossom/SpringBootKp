package zhang.blossom.seckillbyrocketmq;

import cn.hutool.bloomfilter.BitMapBloomFilter;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import zhang.blossom.seckillbyrocketmq.constant.MQConstant;

import java.util.List;
import java.util.UUID;

/**
 * @author: 张锦标
 * @date: 2023/8/17 11:50
 * KeyRocketMQTest类
 */
@SpringBootTest
public class KeyRocketMQTest {
    @Test
    public void testKeyProducer() throws Exception {
        // 创建默认的生产者
        DefaultMQProducer producer = new DefaultMQProducer("test-producer-group");
        // 设置nameServer地址
        producer.setNamesrvAddr(MQConstant.NAMESRV);
        // 启动实例
        producer.start();
        //发送的时候带上一个key uuid
        String uuid = UUID.randomUUID().toString();
        Message msg = new Message("testTopic","test1","key ", "我是一个带标记和key的消息".getBytes());
        SendResult send = producer.send(msg);
        System.out.println(send);
        // 关闭实例
        producer.shutdown();
    }

    @Test
    public void testKeyConsumer() throws Exception {
        // 创建默认消费者组
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("test-producer-group");
        // 设置nameServer地址
        consumer.setNamesrvAddr(MQConstant.NAMESRV);
        // 订阅一个主题来消费   表达式，默认是*,支持"tagA || tagB || tagC" 这样或者的写法 只要是符合任何一个标签都可以消费
        consumer.subscribe("testTopic", "test1 || test2 || test3");
        // 注册一个消费监听 MessageListenerConcurrently是并发消费
        // 默认是20个线程一起消费，可以参看 consumer.setConsumeThreadMax()
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
                                                            ConsumeConcurrentlyContext context) {
                // 这里执行消费的代码 默认是多线程消费
                System.out.println(Thread.currentThread().getName() + "----" + new String(msgs.get(0).getBody()));
                System.out.println(msgs.get(0).getTags());
                System.out.println(msgs.get(0).getKeys());
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        consumer.start();
        System.in.read();
    }

    /**
     * 在boot项目中可以使用@Bean在整个容器中放置一个单利对象
     */
    public static BitMapBloomFilter bloomFilter = new BitMapBloomFilter(100);

    @Test
    public void testRepeatConsumer() throws Exception {
        // 创建默认消费者组
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("test-producer-group");
        consumer.setMessageModel(MessageModel.BROADCASTING);
        // 设置nameServer地址
        consumer.setNamesrvAddr(MQConstant.NAMESRV);
        // 订阅一个主题来消费   表达式，默认是*
        consumer.subscribe("testTopic", "*");
        // 注册一个消费监听 MessageListenerConcurrently是并发消费
        // 默认是20个线程一起消费，可以参看 consumer.setConsumeThreadMax()
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
                                                            ConsumeConcurrentlyContext context) {
                // 拿到消息的key
                MessageExt messageExt = msgs.get(0);
                String keys = messageExt.getKeys();
                // 判断是否存在布隆过滤器中
                if (bloomFilter.contains(keys)) {
                    System.out.println("当前key已经被处理过了:"+keys);
                    // 直接返回了 不往下处理业务
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
                // 这个处理业务，然后放入过滤器中
                // do sth...
                System.out.println("处理一个key："+keys);
                bloomFilter.add(keys);
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        consumer.start();
        System.in.read();
    }

}
