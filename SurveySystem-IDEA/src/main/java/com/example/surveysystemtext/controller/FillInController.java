package com.example.surveysystemtext.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.surveysystemtext.entity.*;
import com.example.surveysystemtext.mapper.FillInMapper;
import com.example.surveysystemtext.mapper.OptionMapper;
import com.example.surveysystemtext.mapper.UserMapper;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("/FillIn")
public class FillInController {
    @Resource
    private FillInMapper fillInMapper;

    @Resource
    private OptionMapper optionMapper;

    @Resource
    private UserMapper userMapper;

    @GetMapping("/AllFillIn") //所有填写信息
    public CommonResult<List<Fill_in>> AllFillIn(){
        return new CommonResult<>(fillInMapper.selectList(new QueryWrapper<>(null)));
    }

    @PostMapping("/InsertFillIn") //保存填写信息
    public CommonResult<Survey> InsertFillIn(@RequestBody Survey survey){
        if(survey.getSurveyId() == null || survey.getQuestionList() == null){
            return new CommonResult<>(400, "保存失败");
        }
        /*
         * 如果填写问卷的用户id为空，则通过UUID新建用户获取id再赋值
         */
        if(survey.getCreatedUserId() == null){
            User user = new User();
            UUID userName = UUID.randomUUID();
            user.setName(userName.toString());
            user.setPassword("123456");
            userMapper.insert(user);
            survey.setCreatedUserId(user.getUserId());
        }
        List<Question> questionList = survey.getQuestionList();
        for(var q : questionList){
            /*
             * 获取题目类型
             * 如果是选择题，则读取content中的选项id并插入fill_in表
             * 如果是填空题，则先将选项插入option_info表获取optionId后再插入fill_in表
             */
            int qtype = q.getType();
            String s = q.getContent();
            if(qtype == 1 || qtype == 2){
                /*
                 * 选择题处理
                 */
                Long oid = 0L;
                for(int i = 0; i < s.length(); i ++){
                    if(s.charAt(i) == ','){
                        Fill_in fillIn = new Fill_in();
                        fillIn.setSurveyId(survey.getSurveyId());
                        fillIn.setQuestionId(q.getQuestionId());
                        fillIn.setUserId(survey.getCreatedUserId());
                        fillIn.setOptionId(oid);
                        fillInMapper.insert(fillIn);
                    } else {
                        oid = oid * 10 + s.charAt(i) - '0';
                    }
                }
            } else {
                /*
                 * 填空题处理
                 */
                Option option = new Option();
                option.setContent(q.getContent());
                option.setQuestionId(q.getQuestionId());
                optionMapper.insert(option);
                Fill_in fillIn = new Fill_in();
                fillIn.setOptionId(option.getOptionId());
                fillIn.setSurveyId(survey.getSurveyId());
                fillIn.setUserId(survey.getCreatedUserId());
                fillIn.setQuestionId(q.getQuestionId());
                fillInMapper.insert(fillIn);
            }
        }
        return new CommonResult<>(survey);
    }

}
