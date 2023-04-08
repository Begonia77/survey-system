package com.example.surveysystemtext.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("option_info")
@Data
public class Option {
    private Long questionId;
    @TableId
    private Long optionId;
    private String content;
    @TableField(exist = false)
    private int count;
}
