package com.example.surveysystemtext.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.surveysystemtext.entity.Option;
import com.example.surveysystemtext.entity.Question;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OptionMapper extends BaseMapper<Option> {

//    int InsertOption(Option option);//添加选项
//    int UpdateOption(Option option);//修改选项
//    int DeleteOption(Option option);//删除选项

    List<Option> FindOption(Question question);//根据问题id查询所有选项信息

}
