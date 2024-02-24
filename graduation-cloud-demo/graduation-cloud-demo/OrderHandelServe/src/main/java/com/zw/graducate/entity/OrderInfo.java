package com.zw.graducate.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author ZhangWei
 * @version 1.0
 * Create by 2024/1/31 20:52
 */

@Data
@TableName("all_order_info")
public class OrderInfo {

       @TableField("ORDER_ID")
       private String orderId;

       @TableField("PRODUCT_ID")
       private String productId;

       @TableField("OWNER_ID")
       private String ownerId;

       @TableField("BUYER_ID")
       private String buyerId;

       @TableField("CREATE_TIME")
       private String createTime;

       @TableField("END_TIME")
       private String endTime;

       @TableField("STATE")
       private String state;

       @TableField("TYPE")
       private char type;

       @TableField("BARGAIN")
       private String bargain;

       @TableField("FINAL_PAYMENT")
       private String finalPayMent;

       @TableField(exist = false)
       private UserInfo userInfo;

       @TableField(exist = false)
       private NewProductDetails newProductDetails;

       @TableField(exist = false)
       private FirstHeadProductDTO firstHeadProductDTO;

       @TableField(exist = false)
       private DoubelRentProductDetails doubelRentProductDetails;

}
