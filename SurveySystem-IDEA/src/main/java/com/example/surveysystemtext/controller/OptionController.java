package com.example.surveysystemtext.controller;

import com.example.surveysystemtext.entity.Option;
import com.example.surveysystemtext.entity.Question;
import com.example.surveysystemtext.mapper.OptionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin
@RestController
@RequestMapping("/Option")
public class OptionController {
    @Autowired
    private OptionMapper optionMapper;
//    @PostMapping("/FindOption")//根据问题id查询所有选项
//    public List<Option> FindOption(@RequestBody Question question){
//        return optionMapper.FindOption(question);
//    }
//
//    @PostMapping("/InsertOption")//添加选项
//    public int InsertOption(@RequestBody Option option){
//        return optionMapper.insert(option);
//    }
//
//    @PostMapping("/UpdateOption")//修改选项
//    public int UpdateOption(@RequestBody Option option){
//        return optionMapper.updateById(option);
//    }
//
//    @PostMapping("/DeleteOption")//删除选项
//    public int DeleteOption(@RequestBody Option option){
//        return optionMapper.deleteById(option);
//    }
}
