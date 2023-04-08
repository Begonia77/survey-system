package com.example.surveysystemtext.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.surveysystemtext.entity.CommonResult;
import com.example.surveysystemtext.entity.Fill_in;
import com.example.surveysystemtext.entity.Option;
import com.example.surveysystemtext.mapper.FillInMapper;
import com.example.surveysystemtext.mapper.OptionMapper;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/FillIn")
public class FillInController {
    @Resource
    private FillInMapper fillInMapper;

    @Resource
    private OptionMapper optionMapper;

    @GetMapping("/AllFillIn") //所有填写信息
    public CommonResult<List<Fill_in>> AllFillIn(){
        return new CommonResult<List<Fill_in>>(fillInMapper.selectList(new QueryWrapper<>(null)));
    }

    @PostMapping("/InsertFillIn") //保存填写信息
    public CommonResult<Fill_in> InsertFillIn(@RequestBody Fill_in fillIn){
        if(fillIn.getSurveyId() == null || fillIn.getQuestionId() == null || fillIn.getUserId() == null){
            return new CommonResult<>(400, "保存失败");
        }
        if(fillIn.getOptionId() == null){
            if(fillIn.getFillInfo() == null){
                return new CommonResult<>(400, "保存失败");
            }
            Option option = new Option();
            option.setQuestionId(fillIn.getQuestionId());

            option.setContent(fillIn.getFillInfo());
            optionMapper.insert(option);
            var oid = option.getOptionId();
            var o1 = optionMapper.selectById(oid);
            if(o1 == null){
                return new CommonResult<>(400, "保存失败");
            }
            fillIn.setOptionId(option.getOptionId());
        }
        fillInMapper.insert(fillIn);
        var fid = fillIn.getFillId();
        var fill1 = fillInMapper.selectById(fid);
        if(fill1 == null) {
            return new CommonResult<>(400, "保存失败");
        } else {
            return new CommonResult<>(fill1);
        }
    }
}
