package blossom.project.springbootkp.seckillservice.listener;

import blossom.project.springbootkp.seckillservice.service.GoodsService;
import blossom.project.springbootkp.seckillservice.service.impl.GoodsServiceImpl;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author: 张锦标
 * @date: 2023/8/20 18:44
 * SecKillListener类
 */
@Component
@RocketMQMessageListener(topic = "seckillTopic", consumerGroup = "seckillGroup", consumeMode =
        ConsumeMode.CONCURRENTLY, consumeThreadNumber = 40)
public class SecKillListener implements RocketMQListener<MessageExt> {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    //如果有多个线程进来当前方法 那么由于只有一个线程可以拿到锁1
    //那么如果直接让其他线程就走else返回了 那么这个线程就永远拿不到锁去操作了
    //所以我们设定一个自旋锁时间 让他走while去自旋

    private static final int ZX_WAIT_TIME = 10000;

    /**
     * 方案:使用分布式锁 mysql或者redis都可以完成
     * 如下代码使用的就是Redis做分布式锁
     * 好处是性能高 并且可以减少mysql的性能压力
     * @param msg
     */
    @Override
    public void onMessage(MessageExt msg) {
        String body = new String(msg.getBody());
        //userId-goodsId
        Integer userId = Integer.parseInt(body.split("-")[0]);
        Integer goodsId = Integer.parseInt(body.split("-")[1]);
        int currentThreadTime = 0;
        //这里的自旋时间应该设定的长一点
        //不然还是有可能出现部分线程拿不到锁然后就直接执行结束返回了
        while (currentThreadTime < ZX_WAIT_TIME) {
            //获取分布式锁
            Boolean flag = redisTemplate.opsForValue()
                    .setIfAbsent("lock:" + goodsId, "",
                            30, TimeUnit.SECONDS);
            if (flag) {
                //拿到锁成功
                try {
                    //如果代码在这里卡住了 是不会走finaly的 就不会删除锁
                    //其他线程也会拿不到锁 所以我们要设定锁的超时时间
                    goodsService.realSeckill(userId, goodsId);
                    return;
                } finally {
                    //删除锁
                    redisTemplate.delete("lock:" + goodsId);
                }
            } else {
                //没有拿到锁的就进行自旋
                currentThreadTime+=200;
                //由于当前线程没拿到锁 那么马上再去拿大概率也拿不到
                //所以为了减少对CPU的消耗 可以睡眠一段时间
                try {
                    Thread.sleep(200L);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    /**
     * 扣减库存
     * 写订单表
     *  使用JVM提供的锁机制来解决
     *  缺点是不支持集群部署项目
     *  只支持单机部署的项目 因为锁不能跨进程
     * @param msg
     */
    //@Override
    //public void onMessage(MessageExt msg) {
    //    String body = new String(msg.getBody());
    //    //userId-goodsId
    //    Integer userId = Integer.parseInt(body.split("-")[0]);
    //    Integer goodsId = Integer.parseInt(body.split("-")[1]);
    //    //方法1:直接在事务方法外面加锁
    //    synchronized (this) {
    //        goodsService.realSeckill(userId, goodsId);
    //    }
    //}

}
