package com.zw.graducate.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

/**
 * @author ZhangWei
 * @version 1.0
 * Create by 2024/2/19 20:48
 */
@Data
public class EsFirstHeadProductBean {

        private String ID;

        private String productID;

        private String productName;

        private Integer price;

        private String userID;

        private char Type;

        private String firstPage;

        private String tagList;

        private String city;

        private String county;

        private String province;

        private String area;

        private String direction;

        private String community;

        private String createTime;

        private char state;

        public String againstReason;


}
