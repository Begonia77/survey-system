package com.example.surveysystemtext.controller;

import com.example.surveysystemtext.entity.Survey;
import com.example.surveysystemtext.mapper.SurveyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin
@RestController
@RequestMapping("/Survey")
public class SurveyController {
    @Autowired
    private SurveyMapper surveyMapper;

    @GetMapping ("/FindAllSurvey")
    List<Survey> findAllSurvey(){
        return surveyMapper.findAllSurvey();
    }

    @PostMapping("/InsertSurvey")//添加问卷
    public int InsertSurvey(@RequestBody Survey survey){
        return surveyMapper.InsertSurvey(survey);
    }

    @PostMapping("/UpdataSurvey")//修改问卷信息
    public int UpdataSurvey(@RequestBody Survey survey){
        return surveyMapper.UpdataSurvey(survey);
    }

    @PostMapping("/FindAllSurveyInfo")//查询问卷的所有问题和选项
    public Survey FindAllSurveyInfo(@RequestBody Survey survey){
        return surveyMapper.FindAllSurveyInfo(survey);
    }

    @PostMapping("/DeleteSurvey")//删除问卷
    public int DeleteSurvey(@RequestBody Survey survey){
        return surveyMapper.DeleteSurvey(survey);
    }
}
