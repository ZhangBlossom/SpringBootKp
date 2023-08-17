package zhang.blossom.seckillbyrocketmq.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: 张锦标
 * @date: 2023/8/17 10:09
 * MsgModel类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MsgModel {
    private String orderSn;
    private Long userId;
    private String desc;  //下单  短信  物流
}
