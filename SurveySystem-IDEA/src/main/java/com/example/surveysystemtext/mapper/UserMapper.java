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
public interface UserMapper {
    List<User> findAll();//查询所有用户

    int count(User user);//查询用户创建问卷个数

    List<Survey> findSurvey(User user);//查询用户创建的所有问卷

    int insertuser(User user);//添加用户

    int UpInfo(User user); //根据id修改用户信息

    User findUser_infoByName(User user);//根据用户名查询用户信息

}
