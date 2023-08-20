package blossom.project.springbootkp.seckillservice;

import blossom.project.springbootkp.seckillservice.mapper.GoodsMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
@MapperScan("blossom.project.springbootkp.seckillservice.mapper")
public class SecKillServiceApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(SecKillServiceApplication.class, args);
    }

}
