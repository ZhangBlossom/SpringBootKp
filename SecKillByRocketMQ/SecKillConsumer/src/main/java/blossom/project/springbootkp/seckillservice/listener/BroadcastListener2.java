package blossom.project.springbootkp.seckillservice.listener;

import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @author: 张锦标
 * @date: 2023/8/18 10:51
 * SimpleMessageListener类
 */
@Component
@RocketMQMessageListener(topic = "broadcastTopic",consumerGroup = "broadcast-consumuer-group-2",
messageModel = MessageModel.BROADCASTING)
public class BroadcastListener2 implements RocketMQListener<String> {

    @Override
    public void onMessage(String msg) {
        System.out.println("当前模式为广播模式2，消息为："+msg);

    }
}
