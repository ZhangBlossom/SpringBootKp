package blossom.project.springbootkp.seckillservice.mapper;

import blossom.project.springbootkp.seckillservice.entity.OrderRecords;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

/**
 * (OrderRecords)表数据库访问层
 *
 * @author 张锦标
 * @since 2023-08-20 17:44:57
 */
@Mapper
public interface OrderRecordsMapper extends BaseMapper<OrderRecords> {
}


