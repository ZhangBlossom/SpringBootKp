package blossom.project.springbootkp.seckillservice.listener;

import blossom.project.springbootkp.seckillservice.entity.MsgModel;
import com.alibaba.fastjson2.JSON;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @author: 张锦标
 * @date: 2023/8/18 11:23
 * OrderlyMessageListener类
 */
@Component
@RocketMQMessageListener(
        topic = "orderlyTopic",
        consumerGroup = "boot-orderly-consumer-group",
        consumeMode = ConsumeMode.ORDERLY, //顺序消费模式 单线程
        maxReconsumeTimes = 5) //最大重试次数
public class OrderlyMessageListener implements RocketMQListener<MessageExt> {


    @Override
    public void onMessage(MessageExt messageExt) {
        MsgModel msgModel = JSON.parseObject(new String(messageExt.getBody()), MsgModel.class);
        System.out.println(msgModel);
    }
}
