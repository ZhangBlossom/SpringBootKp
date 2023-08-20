package blossom.project.springbootkp.seckillservice.service.impl;

import blossom.project.springbootkp.seckillservice.entity.OrderRecords;
import blossom.project.springbootkp.seckillservice.service.OrderRecordsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import blossom.project.springbootkp.seckillservice.mapper.OrderRecordsMapper;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * (OrderRecords表)服务实现类
 *
 * @author 张锦标
 * @since 2023-08-20 17:44:57
 */
@Service("orderRecordsService")
public class OrderRecordsServiceImpl extends ServiceImpl<OrderRecordsMapper, OrderRecords> implements OrderRecordsService {
    @Autowired
    private OrderRecordsMapper orderRecordsMapper;

}


