package blossom.project.springbootkp.seckillservice.config;

import blossom.project.springbootkp.seckillservice.entity.Goods;
import blossom.project.springbootkp.seckillservice.mapper.GoodsMapper;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;

/**
 * @author: 张锦标
 * @date: 2023/8/20 17:56
 * DataSync类
 * 1:每天的10点 晚上的8点把mysql的库存同步到redis中去
 * 2:为了测试方便 希望项目启动的时候 就同步数据
 */
@Configuration
public class DataSync implements InitializingBean, BeanPostProcessor {
    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * bean对象的生命周期
     * 实例化new
     * 属性赋值  对类中的属性进行赋值
     * 初始化  执行初始化方法 自定义一个initMethod方法
     * 前 PostConstruct/中InitializingBean/后BeanPostProcessor
     * 使用
     * 销毁
     * ---------------
     */
    @PostConstruct
    public void initData(){
        List<Goods> goodsList = goodsMapper.selectList(null);
        if (CollectionUtils.isEmpty(goodsList)){
            return;
        }
        //同步数据到Redis中
        goodsList.forEach(goods->{
            redisTemplate.opsForValue().set("goodsId:"+goods.getId(),
                    goods.getStocks().toString());
        });
        System.out.println("同步MySQL到Redis成功!!!");
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }
}
