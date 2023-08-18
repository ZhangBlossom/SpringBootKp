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
import zhang.blossom.seckillbyrocketmq.config.RedisService;
import zhang.blossom.seckillbyrocketmq.constant.MQConstant;
import zhang.blossom.seckillbyrocketmq.entity.MessageIdempotent;
import zhang.blossom.seckillbyrocketmq.mapper.MessageIdempotentMapper;

import java.io.IOException;
import java.sql.*;
import java.util.List;
import java.util.UUID;

/**
 * @author: 张锦标
 * @date: 2023/8/17 14:29
 * RepeatRocketMQTest类
 */
@SpringBootTest(classes = SecKillByRocketMQ.class)
public class RepeatRocketMQTest {
    @Autowired
    private MessageIdempotentMapper mapper;

    @Autowired
    private RedisService redisService;
    //发送一个重复消息
    @Test
    public void repeatProducer() throws MQClientException, MQBrokerException, RemotingException, InterruptedException {
        DefaultMQProducer producer = new DefaultMQProducer("test_producer_group");
        producer.setNamesrvAddr(MQConstant.NAMESRV);
        producer.start();
        String key = UUID.randomUUID().toString();
        Message message1 = new Message("repeatTopic",null,key,"扣减库存-1 ".getBytes());
        Message message2 = new Message("repeatTopic",null,key,"扣减库存-1 ".getBytes());
        producer.send(message1);
        producer.send(message2);
        System.out.println("发送成功,key:"+key);
        producer.shutdown();
    }

    @Test
    /**
     * 我们设计一个去重表 对消息的唯一key添加索引
     * 每次消费的时候先插入数据库 如果成功则执行业务逻辑
     * 如果插入失败则说明已经操作过了，直接返回成功
     * @throws MQClientException
     * @throws IOException
     */
    public void repeatConsumer() throws MQClientException, IOException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("repeatGroup");
        consumer.setNamesrvAddr(MQConstant.NAMESRV);
        consumer.subscribe("repeatTopic","*");
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list,
                                                            ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                MessageExt msg = list.get(0);
                String key = msg.getKeys();
                System.out.println("收到的消息为"+new String(msg.getBody()));
                System.out.println("收到的key为"+key);
                String content = new String(msg.getBody());
                Connection connection = null;
                try {
                    connection = DriverManager.getConnection("jdbc:mysql://123.249.97" +
                                    ".220:3306/test?characterEncoding=utf8" +
                                    "&connectTimeout=1000&socketTimeout=3000&autoReconnect=true&useUnicode=true&useSSL" +
                                    "=false&serverTimezone=UTC",
                            "root", "LiHongyan03");
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                PreparedStatement statement = null;
                try {
                    statement = connection.prepareStatement("insert into message_idempotent " +
                            "(`message_content`,`message_uuid`) values(?,?)");
                    statement.setString(1,content);
                    statement.setString(2,key);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                int update = 0;
                try {
                    update = statement.executeUpdate();
                } catch (SQLException e) {
                    //如果直接抛出异常 那么MQ会把它放入到队列中进行重消费
                    //我们应该在出现重复的处理的时候 直接返回即可
                    //throw new RuntimeException(e);
                    if (e instanceof SQLIntegrityConstraintViolationException){
                        System.out.println("当前的消息的key为："+key+"已经接收过了，不在消费");
                        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                    }
                    //其他错误那么就返回异常信息即可
                    e.printStackTrace();
                }
                System.out.println(update);
                //MessageIdempotent messageIdempotent = new MessageIdempotent();
                //messageIdempotent.setMessageContent(content);
                //messageIdempotent.setMessageUuid(key);
                //int i = mapper.insert(messageIdempotent);
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        consumer.start();
    }
}
