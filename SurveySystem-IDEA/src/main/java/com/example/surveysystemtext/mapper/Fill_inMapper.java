package com.example.surveysystemtext.mapper;

import com.example.surveysystemtext.entity.Fill_in;
import com.example.surveysystemtext.entity.Option;
import com.example.surveysystemtext.entity.Question;
import com.example.surveysystemtext.entity.Survey;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface Fill_inMapper {
    int Count_OptionFill_in(Option option);//查询选项被填写次数

    int Count_SurveyFill_in(Survey survey);//查询问卷被填写次数

}