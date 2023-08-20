package blossom.project.springbootkp.seckillservice.listener;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @author: 张锦标
 * @date: 2023/8/18 10:51
 * SimpleMessageListener类
 */
@Component
@RocketMQMessageListener(topic = "modeTopic",consumerGroup = "mode-consumuer-group")
public class LoadBalanceListener2 implements RocketMQListener<String> {

    /**
     * 这个方法就是消费者方法
     * 这里的String就是消息内容
     * 这里的泛型就是这里的参数类型
     * 如果泛型指定了固定的类型 那么消息体就是我们的参数
     * 如果我们的类型设定为具体的类型 那么我们只能拿到消息体
     * 而如果我们把消息类型设定为MessageExt类型，那么我们可以拿到消息头
     * ------------------------------------------------
     * 只要这个方法不报错 就会直接完成消息的接收 而如果报错了 就会重试
     * @param msg
     */
    @Override
    public void onMessage(String msg) {
        System.out.println("我是第一个分组，集群模式，当前的组为2，消息为："+msg);
    }
}
