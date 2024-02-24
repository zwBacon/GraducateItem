package com.zw.graducate.entity;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author ZhangWei
 * @version 1.0
 * Create by 2024/2/15 19:14
 */
@Data
@TableName("all_videos_info")
public class VideosInfo {

    @TableField("VIDEO_ID")
    private String videoId;

    @TableField("VIDEO_LINK")
    private String videoLink;

    @TableField("PUBLISH_NAME")
    private String publishName;

    @TableField("PUBLISH_IMG")
    private byte[] publishImg;

    @TableField("HOT_DEGREE")
    private String hotDegree;

    @TableField("CREATE_TIME")
    private String createTime;

    @TableField("VIDEO_TITLE")
    private String videoTitle;

    @TableField("VIDEO_SUB_TITLE")
    private String videoSubTitle;

    @TableField(exist = false)
    private JSON videoSrc;

}
