package com.zw.graducate.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author ZhangWei
 * @version 1.0
 * Create by 2023/12/10 22:10
 */
@Data
@TableName("offical_general_accounts")
public class OfficalAccount {

    @TableField(value = "ACCOUNT_NAME")
    private String accountName;

    @TableField(value = "ACCOUNT_INTRO")
    private String accountIntro;

    @TableField(value = "ACCOUNT_IMG")
    private byte[] accountImg;

    @TableField(value = "ACCOUNT_DETAIL")
    private String accountDetail;

    @TableField(value = "ACCOUNT_INDEX")
    private Integer accountIndex;


}
