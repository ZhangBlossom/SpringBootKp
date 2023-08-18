package zhang.blossom.seckillbyrocketmq;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestComponent;
import zhang.blossom.seckillbyrocketmq.constant.MQConstant;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * @author: 张锦标
 * @date: 2023/8/17 21:32
 * RetryRocketMQTest类
 */
@SpringBootTest
public class RetryRocketMQTest {
    @Test
    public void retryProducer() throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer("test_producer_group");
        producer.setNamesrvAddr(MQConstant.NAMESRV);
        //生产者发送消息 重试次数
        producer.setRetryTimesWhenSendFailed(2);
        producer.setRetryTimesWhenSendAsyncFailed(2);
        producer.start();
        String key = UUID.randomUUID().toString();
        Message message1 = new Message("testTopic",null,key,"重试机制 ".getBytes());
        producer.send(message1);
        System.out.println("发送成功,key:"+key);
        producer.shutdown();
    }

    /**
     * 重试的时间间隔就是rocketmq里面设定的延迟消息的时间间隔来定制的
     * 默认重试16次 因为延迟消息的时间间隔有16个等级
     * 1：如果重试了16次都是失败的怎么办？
     * 并发模式下（会被认为是一个死信消息） 顺序模式下（无限次重试）
     * 如果放到死信主题中则主题为 %DLQ%+原有的消费者组的名称
     * 所以可以编写一个消费者组去监听死信topic中的消息
     * 2：能否自定义重试次数？  (可以）
     * 3：当消息处理失败的时候 该如何正确的处理？
     * @throws MQClientException
     * @throws IOException
     */
    @Test
    public void consume() throws MQClientException, IOException {
        //创建一个消费者
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("test_consumer_group");
        consumer.setNamesrvAddr(MQConstant.NAMESRV);
        //设置最大重试次数
        consumer.setMaxReconsumeTimes(1);
        // * 标识订阅这个主题中的所有消息  后期会有消息过滤
        consumer.subscribe("testTopic", "*");
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext context) {
                //这个就是对应的消费方法  业务处理
                //消息如果消费失败 那么就要重新放入到消费队列
                MessageExt ext = list.get(0);
                System.out.println("当前系统时间"+System.currentTimeMillis());
                System.out.println("收到的消息为："+new String(ext.getBody()));
                System.out.println("当前的重试次数为："+ext.getReconsumeTimes());
                //返回值如果为  null/报错/RECONSUMER_LATER 代表消费失败 都会进行消费重试
                //消息会重新回到队列 然后过一会在投递给当前消费者或者其他消费者
                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
            }
        });
        //启动
        consumer.start();
        //挂起当前的JVM
        System.in.read();
    }
    ///////////直接监听死信队列 记录下来 记录下来然后通知运维/////////
    //////////可以是可以 但是不够优雅 推荐下面的那种//////////
    @Test
    public void DLQConsume() throws MQClientException, IOException {
        //创建一个消费者
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("test_consumer_group");
        consumer.setNamesrvAddr(MQConstant.NAMESRV);
        //设置最大重试次数
        consumer.setMaxReconsumeTimes(1);
        // * 标识订阅这个主题中的所有消息  后期会有消息过滤
        consumer.subscribe("%DLQ%test_consumer_group", "*");
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext context) {
                //这个就是对应的消费方法  业务处理
                //消息如果消费失败 那么就要重新放入到消费队列
                MessageExt ext = list.get(0);
                System.out.println("当前系统时间"+System.currentTimeMillis());
                System.out.println("收到的消息为："+new String(ext.getBody()));
                System.out.println("当前的重试次数为："+ext.getReconsumeTimes());
                System.out.println("通知消息到MySQL或者人工。。。。");
                //返回值如果为  null/报错/RECONSUMER_LATER 代表消费失败 都会进行消费重试
                //消息会重新回到队列 然后过一会在投递给当前消费者或者其他消费者
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        //启动
        consumer.start();
        //挂起当前的JVM
        System.in.read();
    }
    //////使用这种方式去解决重试次数比较多////
    @Test
    public void consumer() throws MQClientException, IOException {
        //创建一个消费者
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("test_consumer_group");
        consumer.setNamesrvAddr(MQConstant.NAMESRV);
        //设置最大重试次数
        consumer.setMaxReconsumeTimes(1);
        // * 标识订阅这个主题中的所有消息  后期会有消息过滤
        consumer.subscribe("%DLQ%test_consumer_group", "*");
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext context) {
                MessageExt messageExt = list.get(0);
                try {
                    handleDB();
                }catch (Exception e){
                    //捕获到操作异常
                    //判断消息的重试次数 如果过多就不重试了 直接记录日志 就好
                    int reconsumeTimes = messageExt.getReconsumeTimes();
                    if (reconsumeTimes>=3){
                        System.out.println("记录日志并且上报给运维 MYSQL等");
                        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                    }
                    //没有超过重试上限那么就继续重试
                    return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        //启动
        consumer.start();
        //挂起当前的JVM
        System.in.read();
    }

    private void handleDB() {
        //进行数据库操作
        //进行事务处理
        //然后这里的异常不需要捕获 而是需要抛出并且记录好日志
    }
}
