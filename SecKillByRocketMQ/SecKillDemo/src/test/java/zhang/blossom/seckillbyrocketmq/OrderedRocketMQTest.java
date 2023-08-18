package zhang.blossom.seckillbyrocketmq;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.*;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import zhang.blossom.seckillbyrocketmq.constant.MQConstant;
import zhang.blossom.seckillbyrocketmq.entity.MsgModel;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author: 张锦标
 * @date: 2023/8/17 9:58
 * OrderedRocketMQTest类
 */

@SpringBootTest
public class OrderedRocketMQTest {
    private List<MsgModel> msgModels = Arrays.asList(
            new MsgModel("qwer", 1L, "下单"),
            new MsgModel("qwer", 1L, "短信"),
            new MsgModel("qwer", 1L, "物流"),

            new MsgModel("zxcv", 2L, "下单"),
            new MsgModel("zxcv", 2L, "短信"),
            new MsgModel("zxcv", 2L, "物流")
    );
    //发送顺序消息
    @Test
    public void orderedProducer() throws MQClientException, MQBrokerException, RemotingException, InterruptedException {
        DefaultMQProducer producer = new DefaultMQProducer("test-producer-group");
        producer.setNamesrvAddr(MQConstant.NAMESRV);
        producer.start();
        //发送顺序消息 发送时要确保有序  并且要发送到同一个队列下面去
        msgModels.forEach(msgModel -> {
            Message message = new Message("testTopic",msgModel.toString().getBytes());
            try {
                //发送  相同的订单号应该去相同的队列
                producer.send(message, new MessageQueueSelector() {
                    //这里的send方法的第三个参数arg 就是这个队列选择器的第三个参数 会传递过来
                    @Override
                    public MessageQueue select(List<MessageQueue> list, Message message, Object arg) {
                        //这个方法的返回值就是要选择的队列
                        //这里可以用hash的方式就可以选择到同样的队列了
                        int hash = arg.toString().hashCode();
                        int index = hash % list.size();
                        return list.get(index);
                    }
                }, msgModel.getOrderSn());
            } catch (MQClientException e) {
                throw new RuntimeException(e);
            } catch (RemotingException e) {
                throw new RuntimeException(e);
            } catch (MQBrokerException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        producer.shutdown();
        System.out.println("发送完毕");
    }

    @Test
    public void orderedConsumer() throws MQClientException, IOException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("test-producer-group");
        consumer.setNamesrvAddr(MQConstant.NAMESRV);
        consumer.subscribe("testTopic", "*");
        //MessageListenerConcurrently 并发模式  多线程的  失败后最多重试16次 然后放入死信队列
        //MessageListenerOrderly 顺序模式 单线程的  失败后无限次重试 Integer.MAX_VALUE
        consumer.registerMessageListener(new MessageListenerOrderly() {
            //顺序模式只有一个线程来执行消费
            @Override
            public ConsumeOrderlyStatus consumeMessage(List<MessageExt> list,
                                                       ConsumeOrderlyContext consumeOrderlyContext) {
                //这里的一个线程是一个队列一个线程
                System.out.println(new String(list.get(0).getBody()));
                return ConsumeOrderlyStatus.SUCCESS;
            }
        });
        consumer.start();
        System.in.read();
    }
}
