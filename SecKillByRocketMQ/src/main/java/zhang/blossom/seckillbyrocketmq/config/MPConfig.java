package zhang.blossom.seckillbyrocketmq.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author: 张锦标
 * @date: 2023/8/17 14:47
 * MPConfig类
 */
@Configuration
@MapperScan(basePackages = "zhang.blossom.seckillbyrocketmq.mapper.*")
public class MPConfig {

}
