package zhang.blossom.seckillbyrocketmq.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: 张锦标
 * @date: 2023/8/17 14:21
 * MessageIdempotent类
 */
@Data
@TableName("message_idempotent")
public class MessageIdempotent {

    @TableId(value = "message_id",type = IdType.AUTO)
    private Long messageId;

    @TableField("message_content")
    private String messageContent;

    @TableField("message_uuid")
    private String messageUuid;
}
