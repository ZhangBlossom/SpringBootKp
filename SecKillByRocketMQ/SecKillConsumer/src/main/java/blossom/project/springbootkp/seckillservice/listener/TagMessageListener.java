package blossom.project.springbootkp.seckillservice.listener;

import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.annotation.SelectorType;
import org.apache.rocketmq.spring.core.RocketMQListener;

/**
 * @author: 张锦标
 * @date: 2023/8/18 12:04
 * TagMessageListener类
 */

@RocketMQMessageListener(topic = "bootTestTopic",
    consumerGroup = "boot-tag-consumer-group",
selectorType = SelectorType.TAG, //tag过滤模式
selectorExpression = "tagA || tagB") //tag标签匹配模式
public class TagMessageListener implements RocketMQListener<MessageExt> {
    @Override
    public void onMessage(MessageExt msg) {
        System.out.println(new String(msg.getBody()));
    }
}
