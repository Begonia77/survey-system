package com.example.surveysystemtext;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.surveysystemtext.mapper")
public class SurveySystemTextApplication {


    public static void main(String[] args) {
        SpringApplication.run(SurveySystemTextApplication.class, args);
    }



}
