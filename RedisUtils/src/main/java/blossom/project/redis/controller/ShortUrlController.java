package blossom.project.redis.controller;

import blossom.project.redis.utils.ShortUrlGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author: 张锦标
 * @date: 2023/9/6 14:55
 * ShortUrlController类
 */
@RestController
public class ShortUrlController {
    @Autowired
    private StringRedisTemplate redisTemplate;

    //将长字符串进行短化
    @GetMapping("/encode")
    public String encode(String url){
        //一个长连接URL转换为4个短加密串key
        String[] keys = ShortUrlGenerator.shortUrl(url);
        //任意取出其中一个 比如第一个
        String key = keys[0];
        //用hash彳亍 key=加密串，value=原始url的方式
        redisTemplate.opsForHash().put("short:url",key,url);
        return "http://localhost:8090/"+key;
    }

    //请求重定向 解析短字符串
    @GetMapping("/{key}")
    public void decode(@PathVariable("key") String key, HttpServletResponse response){
        //到redis中把原始的url取出来
        String url = (String) redisTemplate.opsForHash().get("short:url",key);
        try {
            response.sendRedirect(url);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
