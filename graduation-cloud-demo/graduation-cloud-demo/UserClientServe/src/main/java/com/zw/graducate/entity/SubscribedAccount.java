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
 * Create by 2023/12/10 22:59
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("offical_account_subsribe")
public class SubscribedAccount {

    @TableField(value = "SUBSCRIBED_USERNAME")
    private String subscribedUserName;

    @TableField(value = "SUBSCRIBED_ACCOUNTNAME")
    private String subscribedAccountName;

    @TableField(value = "SUBSCRIBED_USERID")
    private String subscribedUserID;

    @TableField(value = "SUBSCRIBED_ACCOUNT_INDEX")
    private Integer subscribedAccountIndex;

    @TableField(value = "SUBSCRIBED_TIME")
    private LocalDateTime subscribedTime;

    @TableField(value = "DESUBCRIBED_TIME")
    private LocalDateTime desubscribedTime;

    @TableField(value = "IS_SUBSCRIBED")
    private Integer isSubscribed;

}
