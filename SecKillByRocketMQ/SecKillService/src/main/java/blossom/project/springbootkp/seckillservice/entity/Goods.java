package blossom.project.springbootkp.seckillservice.entity;




import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("goods")
public class Goods implements Serializable {
    private static final long serialVersionUID = -12504521226283364L;
    
    private Integer id;
    
    private String goodsName;
    
    private Double price;
    
    private Integer stocks;
    
    private Integer status;
    
    private String pic;
    
    private Date createTime;
    
    private Date updateTime;

    @Override
    public String toString(){
        return "blossom.project.springbootkp.seckillservice.entity.Goods {" +
            "id : " + id + ", " +
            "goodsName : " + goodsName + ", " +
            "price : " + price + ", " +
            "stocks : " + stocks + ", " +
            "status : " + status + ", " +
            "pic : " + pic + ", " +
            "createTime : " + createTime + ", " +
            "updateTime : " + updateTime + ", " +
        '}';
    }
}


