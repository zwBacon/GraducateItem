package com.zw.graducate.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author ZhangWei
 * @version 1.0
 * Create by 2024/1/1 13:22
 */
@Data
@TableName("all_product_infos")
public class FirstHeadProductDTO {

    @TableField(value = "ID")
    private String ID;


    @TableField(value = "PRODUCT_ID")
    private String productID;

    @TableField(value = "PRODUCT_NAME")
    private String productName;

    @TableField(value = "PRICE")
    private Integer price;

    @TableField(value = "USERID")
    private String userID;

    @TableField(value = "TYPE")
    private char Type;

    @TableField(value = "FIRSETPAGE")
    private byte[] firstPage;

    @TableField(value = "TAGLIST")
    private String tagList;

    @TableField(value = "CITY")
    private String city;

    @TableField(value = "COUNTY")
    private String county;

    @TableField(value = "PROVINCE_NAME")
    private String province;

    @TableField(value = "AERA")
    private String area;

    @TableField(value = "DIRECTION")
    private String direction;

    @TableField(value = "COMMUNITY")
    private String community;

    @TableField(value = "CREATE_TIME")
    private String createTime;

    @TableField(exist = false)
    private String priceDescription;

    @TableField(exist = false)
    private String pricePerMeter;

    @TableField(value = "STATE")
    private char state;

    @TableField(exist = false)
    private UserInfo userInfo;

    @TableField(value = "AGAINST_REASON")
    public String againstReason;

}
