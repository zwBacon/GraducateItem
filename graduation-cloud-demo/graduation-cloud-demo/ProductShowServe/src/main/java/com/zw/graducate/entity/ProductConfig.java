package com.zw.graducate.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author ZhangWei
 * @version 1.0
 * Create by 2024/1/16 11:58
 */
@Data
@TableName("tag_data")
public class ProductConfig {

    @TableField("TAG_TYPE_ID")
    private String tagTypeId;

    @TableField("TAG_NAME")
    private String tagName;

    @TableField("DATA_ID")
    private String dataId;

    @TableField("REMARK")
    private String remark;

    @TableField("VALID_FLAG")
    private Integer validFlag;

}
