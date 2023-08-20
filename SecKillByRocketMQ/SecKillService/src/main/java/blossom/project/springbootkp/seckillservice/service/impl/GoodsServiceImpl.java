package blossom.project.springbootkp.seckillservice.service.impl;


import blossom.project.springbootkp.seckillservice.entity.Goods;

import blossom.project.springbootkp.seckillservice.entity.OrderRecords;
import blossom.project.springbootkp.seckillservice.mapper.OrderRecordsMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import blossom.project.springbootkp.seckillservice.mapper.GoodsMapper;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import blossom.project.springbootkp.seckillservice.service.GoodsService;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;


/**
 * (Goods表)服务实现类
 *
 * @author 张锦标
 * @since 2023-08-20 17:44:56
 */
@Service("goodsService")
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements GoodsService {
    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private OrderRecordsMapper orderRecordsMapper;

    @Override
    public void realSeckill(Integer userId, Integer goodsId) {

    }


    /**
     * MySQL行锁方案
     * 适合用于并发量比较少的场景 不适合高并发场景
     * 扣减库存
     * 写订单表
     * @param userId
     * @param goodsId
     */
    //@Override
    //@Transactional(rollbackFor = Exception.class)
    //public void realSeckill(Integer userId, Integer goodsId) {
    //    // update goods set stock = stock -1 ,update_time = now() where id = #{goodsId}
    //    //数据库操作的时候会使用如上的操作 那么由于这里会使用到行锁
    //    //所以就可以保证同一时间只有一个线程可以消费成功
    //    //这种方案就类似于使用mysql的行锁的方式来控制并发 确实是可以的 是安全的
    //    //但是这样子对于mysql数据库压力就会特别的大
    //    int i = goodsMapper.updateStock(goodsId);
    //    if (i>0){
    //        OrderRecords orderRecords = new OrderRecords();
    //        orderRecords.setGoodsId(goodsId);
    //        orderRecords.setUserId(userId);
    //        orderRecords.setCreateTime(new Date());
    //        orderRecordsMapper.insert(orderRecords);
    //    }
    //}

    /**
     * 常规方案
     * 也就是在当前事务方法的外面加锁
     * 对于集群直接失效
     *
     * 扣减库存
     * 写订单表
     * @param userId
     * @param goodsId
     */
    //@Override
    //@Transactional(rollbackFor = Exception.class)
    //public void realSeckill(Integer userId, Integer goodsId) {
    //    Goods goods = goodsMapper.selectById(goodsId);
    //    int finalStock = goods.getStocks()-1;
    //    if (finalStock<0){
    //        throw new RuntimeException("库存不足:"+goodsId);
    //    }
    //    goods.setStocks(finalStock);
    //    goods.setUpdateTime(new Date());
    //    int i = goodsMapper.updateById(goods);
    //    if (i>0){
    //        OrderRecords orderRecords = new OrderRecords();
    //        orderRecords.setGoodsId(goodsId);
    //        orderRecords.setUserId(userId);
    //        orderRecords.setCreateTime(new Date());
    //        orderRecordsMapper.insert(orderRecords);
    //    }
    //}

}


