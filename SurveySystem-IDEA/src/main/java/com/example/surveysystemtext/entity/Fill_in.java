package com.example.surveysystemtext.entity;

import lombok.Data;

@Data
public class Fill_in {
    private int fill_id;
    private int survey_id;
    private int question_id;
    private int option_id;
    private int user_id;
    private String fill_in_time;
}
