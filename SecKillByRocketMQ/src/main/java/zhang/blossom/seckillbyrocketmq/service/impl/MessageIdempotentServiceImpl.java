package zhang.blossom.seckillbyrocketmq.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import zhang.blossom.seckillbyrocketmq.entity.MessageIdempotent;
import zhang.blossom.seckillbyrocketmq.mapper.MessageIdempotentMapper;
import zhang.blossom.seckillbyrocketmq.service.MessageIdempotentService;

/**
 * @author: 张锦标
 * @date: 2023/8/17 14:26
 * MessageIdempotentServiceImpl类
 */
@Service
public class MessageIdempotentServiceImpl extends ServiceImpl<MessageIdempotentMapper, MessageIdempotent> implements MessageIdempotentService {
}
