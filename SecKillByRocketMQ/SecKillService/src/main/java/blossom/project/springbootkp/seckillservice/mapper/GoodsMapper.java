package blossom.project.springbootkp.seckillservice.mapper;


import blossom.project.springbootkp.seckillservice.entity.Goods;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * (Goods)表数据库访问层
 *
 * @author 张锦标
 * @since 2023-08-20 17:44:57
 */

@Mapper
public interface GoodsMapper extends BaseMapper<Goods> {

    int updateStock(@Param("goodsId") Integer goodsId);
}


