package com.example.surveysystemtext.entity;

import lombok.Data;

@Data
public class Option {
    private int question_id;
    private int option_id;
    private String content;
    private int count;
}
