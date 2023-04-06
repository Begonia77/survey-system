package com.example.surveysystemtext.entity;

import lombok.Data;

import java.util.List;

@Data
public class Question {
    private int survey_id;
    private int question_id;
    private String question;
    private int question_order;
    private int type;
    private String remark;
    List<Option> OptionList;
}
