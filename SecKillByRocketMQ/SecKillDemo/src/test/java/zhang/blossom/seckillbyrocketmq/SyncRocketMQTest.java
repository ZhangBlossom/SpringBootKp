package zhang.blossom.seckillbyrocketmq;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import zhang.blossom.seckillbyrocketmq.constant.MQConstant;

import java.io.IOException;
import java.util.List;

/**
 * @author: 张锦标
 * @date: 2023/8/16 19:10
 * RocketMQTest类
 */
@SpringBootTest
public class SyncRocketMQTest {
    /**
     * 对于生产者 同一组的生产者可以向不同的topic队列发送消息
     */
    @Test
    public void produce() throws MQClientException, MQBrokerException, RemotingException, InterruptedException {
        DefaultMQProducer producer = new DefaultMQProducer("test-producer-group");
        producer.setNamesrvAddr(MQConstant.NAMESRV);
        producer.start();
        Message message = new Message("testTopic","一个简单的消息".getBytes());
        SendResult sendResult = producer.send(message);
        System.out.println(sendResult.getSendStatus());
        producer.shutdown();
    }

    /**
     * 对于消费者 同一组的消费者只能接收同一个topic的消息
     * 并且如果存在多个消费者组，他们都监听同一个topic的消息
     * 那么就可以选择使用  负载均衡策略 或者 广播策略
     */
    @Test
    public void consume() throws MQClientException, IOException {
        //创建一个消费者
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("test-producer-group");
        consumer.setNamesrvAddr(MQConstant.NAMESRV);
        // * 标识订阅这个主题中的所有消息  后期会有消息过滤
        consumer.subscribe("testTopic", "*");
        //设置一个监听器 （他会一直监听，然后是一个异步回调的机制）
        //那么我们就不能让他start之后这个方法就返回结束 需要挂起当前的JVM(test模式得这样子)
        //正常运行项目的时候项目的JVM会正常运行的 不需要挂起
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext context) {
                //这个就是对应的消费方法  业务处理
                //消息如果消费失败 那么就要重新放入到消费队列

                System.out.println("我是消费者");
                System.out.println(list.get(0).toString());
                System.out.println("消息上下文"+context);
                //返回值如果为null/报错/RECONSUMER_LATER 代表消费失败
                //消息会重新回到队列 然后过一会在投递给当前消费者或者其他消费者
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        //启动
        consumer.start();
        //挂起当前的JVM
        System.in.read();
    }
}
