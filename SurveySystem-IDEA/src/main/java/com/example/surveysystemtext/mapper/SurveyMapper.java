package com.example.surveysystemtext.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.surveysystemtext.entity.Survey;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SurveyMapper extends BaseMapper<Survey> {

    int CountQuestion(Survey survey);//查询问卷问题个数

    List<Survey> findAllSurvey();

    Survey FindAllSurveyInfo(Survey survey);//查询问卷的所有问题和选项



}

