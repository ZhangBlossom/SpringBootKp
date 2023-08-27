package blossom.project.springbootkp.mqconsumer.listener;

import blossom.project.springbootkp.bloomfilter.utils.RepeatFilter;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @author: 张锦标
 * @date: 2023/8/26 9:50
 * RepeatMessageListener类
 */
@Component
@RocketMQMessageListener(topic = "bootTestTopic", consumerGroup = "repear-message-group")
public class RepeatMessageListener implements RocketMQListener<MessageExt> {

    @Autowired
    private RepeatFilter repeatFilter;

    @Override
    public void onMessage(MessageExt messageExt) {
        String msgId = messageExt.getKeys();
        boolean exist = repeatFilter.exist(msgId);
        //判断消息是否消费过
        if (exist) {
            System.out.println("当前消息msgId：" + msgId + "已经消费过。。。");
            //正常消费掉当前消息就好 不要报错
        }
        try {

            String body = new String(messageExt.getBody());
            System.out.println("正常执行消息消费流程，消息内容为：" + body);
            //消费完毕之后将msgId放入到bitmap中
            repeatFilter.add(msgId);
        } catch (Exception e) {
            e.printStackTrace();
            //抛出异常让当前消息再次消费
            throw e;
        }
    }
}
