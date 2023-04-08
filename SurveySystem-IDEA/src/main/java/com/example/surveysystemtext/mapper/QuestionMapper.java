package com.example.surveysystemtext.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.surveysystemtext.entity.Question;
import com.example.surveysystemtext.entity.Survey;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface QuestionMapper extends BaseMapper<Question> {
    List<Question> FindQuestion(Survey survey);//根据问卷id查询所有问题

    Question FindQuestion1(Question question);//根据问题id查询问题
//    int InsertQuestion(Question question);//添加问题
//    int UpdateQuestion(Question question);//修改问题
//    int DeleteQuestion(Question question);//删除问题
}
