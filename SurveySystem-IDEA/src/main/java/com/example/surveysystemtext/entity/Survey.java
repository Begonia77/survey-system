package com.example.surveysystemtext.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.List;
@TableName("survey_info")
@Data
public class Survey {
    @TableId
    private Long surveyId;

    private Long createdUserId;
    private String surveyTitle;
    private String remark;
    private String createdTime;
    private int state;
    private String analysis;
    @TableField(exist = false)
    private int count_question;
    @TableField(exist = false)
    private int count_fill_in;
    @TableField(exist = false)
    private List<Question> QuestionList;

    public boolean QuestionCheckById(Long id){
        for(var q : this.QuestionList){
            if(q.getQuestionId() == id){
                return true;
            }
        }
        return false;
    }
}
