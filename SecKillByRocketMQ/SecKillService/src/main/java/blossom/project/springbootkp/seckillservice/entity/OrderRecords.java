package blossom.project.springbootkp.seckillservice.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("order_records")
public class OrderRecords implements Serializable {
    private static final long serialVersionUID = -55923537136566776L;

    private Integer id;

    private Integer userId;

    private String orderSn;

    private Integer goodsId;

    private Date createTime;

    @Override
    public String toString() {
        return "blossom.project.springbootkp.seckillservice.entity.OrderRecords {" +
                "id : " + id + ", " +
                "userId : " + userId + ", " +
                "orderSn : " + orderSn + ", " +
                "goodsId : " + goodsId + ", " +
                "createTime : " + createTime + ", " +
                '}';
    }
}


