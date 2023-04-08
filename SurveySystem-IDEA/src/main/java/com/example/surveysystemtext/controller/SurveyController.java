package com.example.surveysystemtext.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.surveysystemtext.entity.*;
import com.example.surveysystemtext.mapper.OptionMapper;
import com.example.surveysystemtext.mapper.QuestionMapper;
import com.example.surveysystemtext.mapper.SurveyMapper;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin
@RestController
@RequestMapping("/Survey")
public class SurveyController {
    @Autowired
    private SurveyMapper surveyMapper;

    @Resource
    private OptionMapper optionMapper;

    @Resource
    private QuestionMapper questionMapper;

    @GetMapping ("/FindAllSurvey")//查询所有问卷信息
    public CommonResult<List<Survey>> findAllSurvey(){
        return new CommonResult<>(surveyMapper.findAllSurvey());
    }

    @PostMapping("/InsertSurvey")//添加问卷 输入完整问卷信息
    public CommonResult<Survey> InsertSurvey(@RequestBody Survey survey){
        var ssid = survey.getSurveyId();
        if(surveyMapper.selectById(ssid) != null){
            return new CommonResult<>(400, "添加失败，问卷已存在");
        }
        surveyMapper.insert(survey);
        var sid = survey.getSurveyId();
        /*
         * 插入所有问题
         */
        for(var q : survey.getQuestionList()){
            /*
             * 插入所有选项
             */
            q.setSurveyId(sid);
            questionMapper.insert(q);
             var qid = q.getQuestionId();

            for(var o : q.getOptionList()){
                o.setQuestionId(qid);
                optionMapper.insert(o);
            }
        }
        return new CommonResult<>(survey);
    }

    @PostMapping("/UpdataSurvey")//修改问卷信息 输入完整问卷信息
    public CommonResult<Survey> UpdateSurvey(@RequestBody Survey survey){
        var sid = survey.getSurveyId();
        if(surveyMapper.selectById(sid) == null){
            return new CommonResult<>(400, "更新失败，问卷不存在");
        }
        DeleteSurveyById(survey);
        InsertSurvey(survey);
        Survey survey1 = surveyMapper.FindAllSurveyInfo(survey);
        return new CommonResult<>(survey1);
    }

//    @PostMapping("/UpdataSurvey")//修改问卷信息 输入完整问卷信息
//    public CommonResult<Survey> UpdateSurvey(@RequestBody Survey survey){
//        var sid = survey.getSurveyId();
//        if(surveyMapper.selectById(sid) == null){
//            return new CommonResult<>(400, "更新失败，问卷不存在");
//        }
//
//        /*
//         *  获取原有问卷信息
//         */
//        Survey survey1 = surveyMapper.FindAllSurveyInfo(survey);
//        /*
//         *  获取原有问题集
//         */
//        List<Question> questionList1 = survey1.getQuestionList();
//
//        for (var q1 : questionList1){
//            /*
//             *  将原来有，但现在没有的问题删除
//             */
//            var q1_id = q1.getQuestionId();
//            if(!survey.QuestionCheckById(q1_id)){
//                /*
//                 * 如果原有问题在现有问题集中不存在，则删除问题以及问题的所有选项
//                 */
//                List<Option> optionList1 = q1.getOptionList();
//                if(optionList1 != null){
//                    //删除所有选项
//                    for(var o1 : optionList1){
//                        optionMapper.deleteById(o1.getOptionId());
//                    }
//                }
//                questionMapper.deleteById(q1_id);
//
//            } else {
//                /*
//                 *  如果该问题存在
//                 *  将原来有，但现在没有的选项删除
//                 */
//                Question question = questionMapper.FindQuestion1(q1);
//                List<Option> optionList1 = q1.getOptionList();
//                if(optionList1 != null){
//                    for(var o1 : optionList1){
//                        var o1_id = o1.getOptionId();
//                        /*
//                         * 如果该选项不存在，则删除选项
//                         */
//                        if(!question.OptionCheckById(o1_id)){
//                            optionMapper.deleteById(o1_id);
//                        }
//                    }
//                }
//
//            }
//        }
//
//        /*
//         * 获取现有问题集
//         */
//        List<Question> questionList = survey.getQuestionList();
//        if(questionList != null){
//            for (var question : questionList) {
//                /*
//                 *  将现有，原来没有的问题进行插入
//                 *  将现有，原来也有的问题进行更新
//                 */
//
//
//                if (question.getQuestionId() == null) {
//                    question.setSurveyId(survey.getSurveyId());
//
//                    questionMapper.insert(question);
//
//                    var qid = question.getQuestionId();
//                    /*
//                     *  如果是新问题
//                     *  则将所有问题和所有选项插入
//                     */
//                    List<Option> optionList = question.getOptionList();
//                    if(optionList != null){
//                        for(var option : optionList){
//                            option.setQuestionId(qid);
//                            optionMapper.insert(option);
//                        }
//                    }
//
//                } else {
//                    /*
//                     *  如果是旧问题
//                     *  则将所有问题的所有选项更新
//                     */
//                    List<Option> optionList = question.getOptionList();
//                    if(optionList != null){
//                        for(var option : optionList){
//                            /*
//                             * 若果是新选项则插入
//                             */
//                            if(option.getOptionId() == null){
//                                option.setQuestionId(question.getQuestionId());
//                                optionMapper.insert(option);
//                            } else{
//                                optionMapper.updateById(option);
//                            }
//                        }
//                    }
//                    questionMapper.updateById(question);
//                }
//            }
//        }
//        //surveyMapper.updateById(survey);
//        return new CommonResult<>(0, "修改成功");
//    }

    @PostMapping("/FindSurveyInfoById")//根据id查询问卷的所有问题和选项
    public CommonResult<Survey> FindSurveyInfoById(@RequestBody Survey survey){
        var sid = survey.getSurveyId();
        if(surveyMapper.selectById(sid) == null){
            return new CommonResult<>(400, "查询失败，问卷不存在");
        }
        return new CommonResult<>(surveyMapper.FindAllSurveyInfo(survey));
    }

    @PostMapping("/DeleteSurveyById")//根据问卷id删除整份问卷
    public CommonResult<Survey> DeleteSurveyById(@RequestBody Survey survey){
        var sid = survey.getSurveyId();
        if(surveyMapper.selectById(sid) == null){
            return new CommonResult<>(400, "删除失败，问卷不存在");
        }
        /*
         * 获取原有的问卷信息
         */
        Survey survey1 = surveyMapper.FindAllSurveyInfo(survey);
        List<Question> questionList1 = survey1.getQuestionList();
        surveyMapper.deleteById(survey);
         /*
          * 删除原有问卷问题
          */
        if(questionList1 != null){
            for(var q1 : questionList1){
                List<Option> optionList1 = q1.getOptionList();
                questionMapper.deleteById(q1);
                /*
                 * 删除原有问题选项
                 */
                if(optionList1 != null){
                    for(var o1 : optionList1){
                        optionMapper.deleteById(o1);
                    }
                }
            }
        }
        return new CommonResult<>(0, "删除成功");
    }
}
