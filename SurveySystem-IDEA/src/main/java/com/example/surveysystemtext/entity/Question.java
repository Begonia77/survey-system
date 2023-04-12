package com.example.surveysystemtext.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.List;
import java.util.Objects;

@Data
@TableName("question_info")
public class Question {

    private Long surveyId;
    @TableId
    private Long questionId;
    private String question;
    private int questionOrder;
    private int type;
    private String remark;
    @TableField(exist = false)
    List<Option> OptionList;
    @TableField(exist = false)
    String content;

    public boolean OptionCheckById(Long id){
        for(var o : this.OptionList){
            if(Objects.equals(o.getOptionId(), id)){
                return true;
            }
        }
        return false;
    }
}
