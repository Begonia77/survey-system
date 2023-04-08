package com.example.surveysystemtext.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("fill_in")
@Data
public class Fill_in {
    @TableId
    private Long fillId;
    private Long surveyId;
    private Long questionId;
    private Long optionId;
    private Long userId;
    private String fillInTime;

    @TableField(exist = false)
    private String fillInfo;
}
