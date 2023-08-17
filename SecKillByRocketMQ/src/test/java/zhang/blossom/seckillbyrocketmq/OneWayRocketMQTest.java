package zhang.blossom.seckillbyrocketmq;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import zhang.blossom.seckillbyrocketmq.constant.MQConstant;

/**
 * @author: 张锦标
 * @date: 2023/8/16 22:49
 * OneWayRocketMQTest类
 */
@SpringBootTest
public class OneWayRocketMQTest {
    @Test
    public void testOnewayProducer() throws Exception {
        // 创建默认的生产者
        DefaultMQProducer producer = new DefaultMQProducer("test-producer-group");
        // 设置nameServer地址
        producer.setNamesrvAddr(MQConstant.NAMESRV);
        // 启动实例
        producer.start();
        Message msg = new Message("testTopic", ("单向消息").getBytes());
        // 发送单向消息
        producer.sendOneway(msg);
        // 关闭实例
        producer.shutdown();
    }
}
