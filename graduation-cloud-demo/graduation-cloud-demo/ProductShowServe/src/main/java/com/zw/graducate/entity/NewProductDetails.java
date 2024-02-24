package com.zw.graducate.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author ZhangWei
 * @version 1.0
 * Create by 2024/1/20 20:55
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("new_product_details")
public class NewProductDetails {

       @TableField(value = "Product_ID")
       private String productID;

       @TableField(value = "IS_SUBSCRIB")
       private char isSubscrib;

       @TableField(value = "SUBSCRIBED_USERID")
       private String subscribedUserId;

       @TableField(value = "OPEN_TIME")
       private LocalDateTime openTime;

       @TableField(value = "USAGE")
       private String usage;

       @TableField(value = "ADDRESS")
       private String address;

       @TableField(value = "EQUITY")
       private String equity;

       @TableField(value = "TYPE_OF_BUILD")
       private String typeOfBuild;

       @TableField(value = "RENOVATION")
       private String renovation;

       @TableField(value = "STRATA_COMPANY")
       private String strataCompany;

       @TableField(value = "PROPERTY_FEE")
       private float propertyFee;

       @TableField(value = "WATER_SUPPLY")
       private String waterSupply;

       @TableField(value = "ELECTRIC_SUPPLY")
       private String electricSupply;

       @TableField(value = "HEAT_SUPPLY")
       private String heatSupply;

       @TableField(value = "FLOOR_SPACE")
       private String floorSpace;

       @TableField(value = "AREA")
       private String area;

       @TableField(value = "FLOOR_AREA")
       private float floorArea;

       @TableField(value = "GREEN_RATE")
       private String greenRate;

       @TableField(value = "PLAN_HOUSEHOLD")
       private String planHouseHold;

       @TableField(value = "PARK_AVAILABILITY")
       private String parkAvailability;

       @TableField(value = "PARK_SPACE_RATIO")
       private String parkSpaceRatio;

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
