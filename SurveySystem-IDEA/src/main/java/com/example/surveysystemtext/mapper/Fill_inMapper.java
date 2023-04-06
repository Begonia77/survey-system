package com.example.surveysystemtext.mapper;

import com.example.surveysystemtext.entity.Option;
import com.example.surveysystemtext.entity.Survey;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface Fill_inMapper {
    int Count_OptionFill_in(Option option);//查询选项被填写次数

    int Count_SurveyFill_in(Survey survey);//查询问卷被填写次数

}
