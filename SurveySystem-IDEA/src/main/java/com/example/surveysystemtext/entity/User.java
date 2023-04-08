package com.example.surveysystemtext.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.List;
@TableName("user_info")
@Data
public class User {
    @TableId
    private Long userId;

    private String name;
    private String password;
    private String createdTime;
    @TableField(exist = false)
    private List<Survey> surveyList;

}
