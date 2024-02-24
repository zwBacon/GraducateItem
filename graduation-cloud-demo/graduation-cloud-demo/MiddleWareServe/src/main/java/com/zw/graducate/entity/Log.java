package com.zw.graducate.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author ZhangWei
 * @version 1.0
 * Create by 2023/12/22 10:19
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Log {

    @TableField(value = "CALLTYPE")
    private String callType;

    @TableField(value = "METHOD")
    private String method;

    @TableField(value = "PARAMS")
    private String params;

    @TableField(value = "URL")
    private String url;

    @TableField(value = "IP")
    private String ip;

    @TableField(value = "CALLTIME")
    private LocalDateTime callTime;

    @TableField(value = "RESPONSE")
    private String response;

    @TableField(value = "FOLLOW_ID")
    private String followId;

}

