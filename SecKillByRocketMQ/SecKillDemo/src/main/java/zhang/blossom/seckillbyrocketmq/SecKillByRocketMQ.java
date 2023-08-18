package zhang.blossom.seckillbyrocketmq;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import zhang.blossom.seckillbyrocketmq.mapper.MessageIdempotentMapper;

/**
 * 阿斯顿
 */
@SpringBootApplication
@MapperScan(basePackages = "zhang.blossom.seckillbyrocketmq.mapper")
public class SecKillByRocketMQ {

    public static void main(String[] args) {
        SpringApplication.run(SecKillByRocketMQ.class, args);
    }

}
