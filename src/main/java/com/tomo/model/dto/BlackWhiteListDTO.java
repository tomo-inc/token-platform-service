package com.tomo.model.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName(value = "black_white_list")
public class BlackWhiteListDTO {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "`type`")
    private Boolean type;

    @TableField(value = "address")
    private String address;

    @TableField(value = "chain_id")
    private Long chainId;

    @TableField(value = "`comment`")
    private String comment;

    @TableField(value = "update_time")
    private Date updateTime;

    @TableField(value = "create_time")
    private Date createTime;

    @TableField(value = "`source`")
    private String source;
}