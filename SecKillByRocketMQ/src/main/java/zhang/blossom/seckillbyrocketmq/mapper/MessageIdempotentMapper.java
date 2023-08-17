package zhang.blossom.seckillbyrocketmq.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import zhang.blossom.seckillbyrocketmq.entity.MessageIdempotent;

/**
 * @author: 张锦标
 * @date: 2023/8/17 14:27
 * MessageIdempotentMapper接口
 */
@Mapper
public interface MessageIdempotentMapper extends BaseMapper<MessageIdempotent> {
    MessageIdempotent selectByUUID(@Param("uuid") String uuid);
}
