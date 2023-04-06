package com.example.surveysystemtext.mapper;

import com.example.surveysystemtext.entity.Survey;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SurveyMapper {

    int CountQuestion(Survey survey);//查询问卷问题个数

    List<Survey> findAllSurvey();

    int InsertSurvey(Survey survey);//添加问卷

    int UpdataSurvey(Survey survey);//根据问卷id修改问卷信息

    Survey FindAllSurveyInfo(Survey survey);//查询问卷的所有问题和选项

    int DeleteSurvey(Survey survey);
}

