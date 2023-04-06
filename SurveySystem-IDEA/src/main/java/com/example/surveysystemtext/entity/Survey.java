package com.example.surveysystemtext.entity;

import lombok.Data;

import java.util.List;

@Data
public class Survey {
    private int survey_id;
    private int created_user_id;
    private String survey_title;
    private String remark;
    private String created_time;
    private int state;
    private String analysis;
    private int count_question;
    private int count_fill_in;
    private List<Question> QuestionList;
}
