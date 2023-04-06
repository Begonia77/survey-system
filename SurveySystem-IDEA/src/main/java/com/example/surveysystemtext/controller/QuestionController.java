package com.example.surveysystemtext.controller;

import com.example.surveysystemtext.entity.Question;
import com.example.surveysystemtext.entity.Survey;
import com.example.surveysystemtext.mapper.QuestionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/Question")
public class QuestionController {
    @Autowired
    private QuestionMapper questionMapper;

    @PostMapping("/FindAllQuestion")//根据问卷id查询所有问题
    public List<Question> FindQuestion(@RequestBody Survey survey){
        return questionMapper.FindQuestion(survey);
    }

    @PostMapping("/InsertQuestion")//添加问题
    public int InsertQuestion(@RequestBody Question question){
        return questionMapper.InsertQuestion(question);
    }

    @PostMapping("/UpdateQuestion")//修改问题
    public int UpdateQuestion(@RequestBody Question question){
        return questionMapper.UpdateQuestion(question);
    }

    @PostMapping("/DeleteQuestion")//删除问题
    public int DeleteQuestion(@RequestBody Question question){
        return questionMapper.DeleteQuestion(question);
    }
}
