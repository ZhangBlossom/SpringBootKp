package blossom.project.springbootkp.seckillservice.service;

import blossom.project.springbootkp.seckillservice.entity.Goods;

import java.util.List;
import java.util.Map;

/**
 * (Goods)表服务接口
 *
 * @author 张锦标
 * @since 2023-08-20 17:44:53
 */
public interface GoodsService {

    void realSeckill(Integer userId, Integer goodsId);
}


