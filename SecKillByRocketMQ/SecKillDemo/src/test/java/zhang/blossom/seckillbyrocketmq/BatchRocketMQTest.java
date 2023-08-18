package zhang.blossom.seckillbyrocketmq;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import zhang.blossom.seckillbyrocketmq.constant.MQConstant;

import java.util.Arrays;
import java.util.List;

/**
 * @author: 张锦标
 * @date: 2023/8/16 22:44
 * AsyncRocketMQTest类
 */
@SpringBootTest
public class BatchRocketMQTest {
    @Test
    public void testBatchProducer() throws Exception {
        // 创建默认的生产者
        DefaultMQProducer producer = new DefaultMQProducer("test-producer-group");
        // 设置nameServer地址
        producer.setNamesrvAddr(MQConstant.NAMESRV);
        // 启动实例
        producer.start();
        List<Message> messages = Arrays.asList(
                new Message("testTopic", "批量消息1".getBytes()),
                new Message("testTopic", "批量消息2".getBytes()),
                new Message("testTopic", "批量消息3".getBytes())
        );
        producer.send(messages);
        System.out.println("批量执行任务");
        // 挂起jvm 因为回调是异步的不然测试不出来
        System.in.read();
        // 关闭实例
        producer.shutdown();
    }
}
