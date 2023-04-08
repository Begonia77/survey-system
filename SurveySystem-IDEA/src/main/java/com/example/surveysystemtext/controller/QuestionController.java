package com.example.surveysystemtext.controller;

import com.example.surveysystemtext.entity.Question;
import com.example.surveysystemtext.entity.Survey;
import com.example.surveysystemtext.mapper.QuestionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin
@RestController
@RequestMapping("/Question")
public class QuestionController {
    @Autowired
    private QuestionMapper questionMapper;

//    @PostMapping("/FindAllQuestion")//根据问卷id查询所有问题
//    public List<Question> FindQuestion(@RequestBody Survey survey){
//        return questionMapper.FindQuestion(survey);
//    }

//    @PostMapping("/InsertQuestion")//添加问题
//    public int InsertQuestion(@RequestBody Question question){
//        return questionMapper.InsertQuestion(question);
//    }
//
//
//    @PostMapping("/DeleteQuestion")//删除问题
//    public int DeleteQuestion(@RequestBody Question question){
//        return questionMapper.DeleteQuestion(question);
//    }
}
