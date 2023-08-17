package zhang.blossom.seckillbyrocketmq;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import zhang.blossom.seckillbyrocketmq.constant.MQConstant;

/**
 * @author: 张锦标
 * @date: 2023/8/16 22:44
 * AsyncRocketMQTest类
 */
@SpringBootTest
public class AsyncRocketMQTest {
    @Test
    public void testAsyncProducer() throws Exception {
        // 创建默认的生产者
        DefaultMQProducer producer = new DefaultMQProducer("test-producer-group");
        // 设置nameServer地址
        producer.setNamesrvAddr(MQConstant.NAMESRV);
        // 启动实例
        producer.start();
        Message msg = new Message("testTopic", ("异步消息").getBytes());
        producer.send(msg, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                System.out.println("发送成功");
            }
            @Override
            public void onException(Throwable e) {
                System.out.println("发送失败");
            }
        });
        System.out.println("看看谁先执行");
        // 挂起jvm 因为回调是异步的不然测试不出来
        System.in.read();
        // 关闭实例
        producer.shutdown();
    }
}
