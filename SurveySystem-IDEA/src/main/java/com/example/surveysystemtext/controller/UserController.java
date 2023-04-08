package com.example.surveysystemtext.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.surveysystemtext.entity.CommonResult;
import com.example.surveysystemtext.entity.Survey;
import com.example.surveysystemtext.entity.User;
import com.example.surveysystemtext.mapper.UserMapper;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserMapper userMapper;


    @GetMapping("/FindAllUserInfo") //查询所有用户
    public CommonResult<List<User>> AllUser(){
        return new CommonResult<>(userMapper.selectList(new QueryWrapper<>(null)));
    }

    @PostMapping("/FindUserAllSurveyById") //根据id查询用户创建的所有问卷
    public CommonResult<List<Survey>> UserAllSurvey(@RequestBody User user){
        User u1 = userMapper.selectById(user.getUserId());
        if(u1 == null){
            return new CommonResult<>(400, "用户不存在");
        } else {
            return new CommonResult<>(userMapper.findSurvey(user));
        }

    }

    @PostMapping("/UpdateUserById") //根据id修改用户信息
    public CommonResult<User> UpdateUserInfo(@RequestBody User user){
        User u1 = userMapper.selectById(user.getUserId());
        if(u1 == null){
            return new CommonResult<>(400, "用户不存在");
        } else {
            userMapper.updateById(user);
            return new CommonResult<>(user);
        }
    }

    @PostMapping("/Log_inCheck")//登录检索
    public CommonResult<User> Log_inCheck(@RequestBody User user){
        String userName = user.getName();
        QueryWrapper<User> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.lambda().eq(User::getName, userName);
        User user1 = userMapper.selectOne(queryWrapper1);

        if(user1 == null){
            return new CommonResult<>(400, "用户不存在");
        } else{
                if(user.getPassword().equals(user1.getPassword())){
                    /*
                     * 检索成功
                     */
                    return new CommonResult<>(user1);
                }else {
                    /*
                     * 密码错误
                     */
                    return new CommonResult<>(401, "密码错误");
                }
        }
    }

    @PostMapping("/RegisterCheck")//注册检索
    public CommonResult<User> RegisterCheck(@RequestBody User user){
        String userName = user.getName();
        QueryWrapper<User> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.lambda().eq(User::getName, userName);
        User user1 = userMapper.selectOne(queryWrapper1);

        if(user1 == null){
            userMapper.insert(user);
            QueryWrapper<User> queryWrapper2 = new QueryWrapper<>();
            queryWrapper2.lambda().eq(User::getName, userName);
            User user2 = userMapper.selectOne(queryWrapper2);
            /*
             * 注册成功
             */
            return new CommonResult<>(user2);
        } else {
            /*
             * 注册失败，用户名已经存在
             */
            return new CommonResult<>(400, "注册失败");
        }
    }

}
