package com.zw.graducate.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ZhangWei
 * @version 1.0
 * Create by 2024/1/1 13:21
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("doubel_rent_product_details")
public class DoubelRentProductDetails {

    @TableField(value = "Product_ID")
    private String productID;

    @TableField(value = "IS_SUBSCRIB")
    private char isSubscrib;

    @TableField(value = "SUBSCRIBED_USERID")
    private String subscribedUserId;

    @TableField(value = "FLOOR")
    private String floor;

    @TableField(value = "INSIDE_SET")
    private String insideSet;

    @TableField(value = "FITMENT")
    private String fitment;

    @TableField(value = "LIFT")
    private char lift;

    @TableField(value = "FLOOR_TYPE")
    private String floorType;

    @TableField(value = "TYPE")
    private String type;

    @TableField(value = "OWNERSHIP")
    private String ownerShip;

    @TableField(value = "AGE")
    private String age;

    @TableField(value = "DETAIL_IMG1")
    private byte[] detailImg1;

    @TableField(value = "DETAIL_IMG2")
    private byte[] detailImg2;

    @TableField(value = "DETAIL_IMG3")
    private byte[] detailImg3;

    @TableField(value = "DETAIL_IMG4")
    private byte[] detailImg4;

    @TableField(value = "RATE")
    private String rate;

    @TableField(value = "PRODUCT_VALUE")
    private float productValue;

    @TableField(value = "SUPPORT_VALUE")
    private float supportValue;

    @TableField(value = "TRADE_VALUE")
    private float tradeValue;

    @TableField(value = "AREA_VALUE")
    private float areaValue;

    @TableField(value = "COMMENT")
    private String comment;

    @TableField(exist = false)
    private UserInfo userInfo;

}
