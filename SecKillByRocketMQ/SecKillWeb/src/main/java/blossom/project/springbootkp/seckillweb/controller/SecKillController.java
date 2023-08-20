package blossom.project.springbootkp.seckillweb.controller;

import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: 张锦标
 * @date: 2023/8/20 17:09
 * SecKillController类
 */
@RestController
public class SecKillController {

    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    private AtomicInteger atomicInteger = new AtomicInteger(0);
    /**
     * 1：用户去重
     * 2：库存的预扣减
     * 3：消息放入mq
     * @param goodsId 商品id
     * @param userId 真实的项目不会直接传递userId 而是从请求中获取
     * @return
     */
    @GetMapping("/seckill")
    public String seckill(Integer goodsId,Integer userId){
        //添加一个日期参数 这样子就可以在某天之后删除这些日期key 从而可以再次参加秒杀
        //unique-key = [当前的日期]:userId：goodsId
        //测试的时候暂时先用原子类作为用户id
        String key = atomicInteger.getAndIncrement()+"-"+goodsId;
        Boolean flag = redisTemplate.opsForValue().setIfAbsent(key, "");
        if (!flag){
            return "当前用户已经参与过当前秒杀活动，请购买其他商品";
        }
        //TODO 库存的预扣减 假设Redis中存储的是 goodsId：stock库存
        //记住先查再改在更新是危险的操作 并发不安全的
        Long count = redisTemplate.opsForValue().decrement("goodsId:" + goodsId);
        if (count<0){
            System.out.println("没有库存了..");
            return "您来晚了，当前商品卖完了";
        }
        //放MQ进行异步处理
        rocketMQTemplate.asyncSend("seckillTopic", key, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                System.out.println("发送成功");
            }

            @Override
            public void onException(Throwable throwable) {
                System.out.println("消息发送失败"+throwable.getMessage());
                System.out.println("当前用户的id:"+atomicInteger.get()+",商品id为:"+goodsId);
            }
        });
        return "正在拼命抢购中,请稍后查看订单中心.";
    }
}
