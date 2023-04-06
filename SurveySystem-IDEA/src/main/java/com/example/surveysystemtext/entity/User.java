package com.example.surveysystemtext.entity;

import lombok.Data;

import java.util.List;

@Data
public class User {
    private int user_id;
    private String name;
    private String password;
    private String created_time;
    private List<Survey> surveyList;

}
