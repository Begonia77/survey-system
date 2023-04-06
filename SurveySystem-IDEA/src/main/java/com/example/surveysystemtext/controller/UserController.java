package com.example.surveysystemtext.controller;

import com.example.surveysystemtext.entity.Question;
import com.example.surveysystemtext.entity.Survey;
import com.example.surveysystemtext.entity.User;
import com.example.surveysystemtext.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserMapper userMapper;

    @GetMapping("/AllUserInfo") //查询所有用户
    public List<User> AllUser(){
        return userMapper.findAll();
    }

    @PostMapping("/CountSurvey") //查询用户创建问卷个数
    public int CountSurvey(@RequestBody User user){
        return userMapper.count(user);
    }

    @PostMapping("/UserAllSurvey") //查询用户创建的所有问卷
    public List<Survey> UserAllSurvey(@RequestBody User user){
        return userMapper.findSurvey(user);
    }

    @PostMapping("/InsertUser") //添加用户
    public int InsertUser(@RequestBody User user){
        return userMapper.insertuser(user);
    }

    @PostMapping("/UpdateUserInfo") //根据id修改用户信息
    public int UpdateUserInfo(@RequestBody User user){
        return userMapper.UpInfo(user);
    }

    @PostMapping("FindUser_idByName")//根据用户名查询用户id
    public int FindUser_idByName(@RequestBody User user){
        User u = userMapper.findUser_infoByName(user);
        if(u != null) return u.getUser_id();
        else return -1;
    }

    @PostMapping("FindUser_passwordByName")//根据用户名查询用户password
    public String FindUser_passwordByName(@RequestBody User user){
        User u = userMapper.findUser_infoByName(user);
        if(u != null) return u.getPassword();
        else return "-1";
    }

}
