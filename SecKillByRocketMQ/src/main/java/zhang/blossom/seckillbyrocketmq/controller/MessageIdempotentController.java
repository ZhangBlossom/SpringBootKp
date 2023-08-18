package zhang.blossom.seckillbyrocketmq.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import zhang.blossom.seckillbyrocketmq.entity.MessageIdempotent;
import zhang.blossom.seckillbyrocketmq.mapper.MessageIdempotentMapper;

import java.util.List;

/**
 * @author: 张锦标
 * @date: 2023/8/17 16:05
 * MessageIdempotentController类
 */
@RestController
public class MessageIdempotentController {
    @Autowired
    private MessageIdempotentMapper mapper;
    @GetMapping("/list")
    public List<MessageIdempotent> list(){
        return mapper.selectList(null);
    }
    @PostMapping("/save")
    public Boolean save(@RequestBody MessageIdempotent messageIdempotent){
        mapper.insert(messageIdempotent);
        return true;
    }
}
