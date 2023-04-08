package com.example.surveysystemtext.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.surveysystemtext.entity.Question;
import com.example.surveysystemtext.entity.Survey;
import com.example.surveysystemtext.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User>{

    int count(User user);//查询用户创建问卷个数

    List<Survey> findSurvey(User user);//查询用户创建的所有问卷


}
