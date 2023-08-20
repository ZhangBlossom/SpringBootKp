package blossom.project.springbootkp.seckillservice.entity;

import lombok.Data;

/**
 * @author: 张锦标
 * @date: 2023/8/17 14:21
 * MessageIdempotent类
 */
@Data
public class MessageIdempotent {

    private Long messageId;

    private String messageContent;

    private String messageUuid;
}
