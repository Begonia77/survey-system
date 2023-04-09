package com.example.surveysystemtext.controller;

import com.example.surveysystemtext.entity.*;
import com.example.surveysystemtext.mapper.OptionMapper;
import com.example.surveysystemtext.mapper.QuestionMapper;
import com.example.surveysystemtext.mapper.SurveyMapper;
import com.plexpt.chatgpt.ChatGPT;
import com.plexpt.chatgpt.entity.chat.ChatCompletion;
import com.plexpt.chatgpt.entity.chat.ChatCompletionResponse;
import com.plexpt.chatgpt.entity.chat.Message;
import com.plexpt.chatgpt.util.Proxys;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.net.Proxy;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/Survey")
public class SurveyController {
    Proxy proxy = Proxys.http("127.0.0.1", 7890);
    @Autowired
    private SurveyMapper surveyMapper;
    @Resource
    private OptionMapper optionMapper;
    @Resource
    private QuestionMapper questionMapper;
    @Value("${chatgpt.apiKey}")
    private String apiKey;
    @Value("${chatgpt.apiHost}")
    private String apiHost;

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

    @GetMapping("/FindAllSurvey")//查询所有问卷信息
    public CommonResult<List<Survey>> findAllSurvey() {
        return new CommonResult<>(surveyMapper.findAllSurvey());
    }

//    @PostMapping("/InsertModel")//添加模板
//    public CommonResult<Survey> InsertModel(@RequestBody Survey survey) {
//        var sid = survey.getSurveyId();
//        if (surveyMapper.selectById(sid) == null) {
//            return new CommonResult<>(400, "查询失败，问卷不存在");
//        }
//        //survey1id为新问卷的id，实现自增
//        Survey survey1 = new Survey();
//        surveyMapper.insert(survey1);
//        var sid1 = survey1.getSurveyId();
//        survey1.setSurveyTitle(survey.getSurveyTitle());
//        /*
//         *将survey的所有问题复制给survey1
//         */
//        List<Question> questionList = survey.getQuestionList();
//        for (var q : questionList) {
//            q.setSurveyId(sid1);
//            questionMapper.insert(q);
//            var qid = q.getQuestionId();
//            /*
//             * 将survey的所有选项复制给survey1
//             */
//            List<Option> optionList = q.getOptionList();
//            for (var o : optionList) {
//                o.setQuestionId(qid);
//                optionMapper.insert(o);
//            }
//        }
//
//        //将status设为4，表示为模板
//        survey1.setState(4);
//        //createdUserId为创建者的id
//        survey1.setCreatedUserId(survey.getCreatedUserId());
//        return new CommonResult<>(survey1);
//    }

    @PostMapping("/CreateTemplate") // 通过问卷生成模板
    public CommonResult<Survey> CreateTemplate(@RequestBody Survey survey) {
        // 通过surveyId查找问卷
        Survey oldSurvey = surveyMapper.selectById(survey.getSurveyId());
        if (oldSurvey == null) {
            return new CommonResult<>(400, "生成模板失败，问卷不存在");
        }

        // 设置新模板属性
        survey.setCreatedUserId(1L);
        survey.setSurveyId(null); // 设置为null以实现自增
        survey.setState(4); // 设置为4
        survey.setAnalysis(null); // 去掉原有分析
        survey.setRemark(survey.getRemark()); // 设置备注
        survey.setSurveyTitle(oldSurvey.getSurveyTitle()); // 设置标题

        // 插入新模板
        surveyMapper.insert(survey);
        var sid = survey.getSurveyId();
        /*
         *  将oldSurvey进行插入到新模板中
         */
        List<Question> newQuestionList = new ArrayList<>();
        List<Question> questionList = oldSurvey.getQuestionList();
        if (questionList != null) {
            for (var q : questionList) {
                q.setSurveyId(sid);
                questionMapper.insert(q);
                var qid = q.getQuestionId();
                List<Option> optionList = q.getOptionList();
                if (optionList != null) {
                    for (var o : optionList) {
                        o.setQuestionId(qid);
                        optionMapper.insert(o);
                    }
                }
                newQuestionList.add(q);
            }
        }
        survey.setQuestionList(newQuestionList);
        return new CommonResult<>(survey);
    }


    @PostMapping("/InsertSurvey")//添加问卷 输入完整问卷信息
    public CommonResult<Survey> InsertSurvey(@RequestBody Survey survey) {
        var ssid = survey.getSurveyId();
        if (surveyMapper.selectById(ssid) != null) {
            return new CommonResult<>(400, "添加失败，问卷已存在");
        }
        surveyMapper.insert(survey);
        var sid = survey.getSurveyId();
        /*
         * 插入所有问题
         */
        for (var q : survey.getQuestionList()) {
            /*
             * 插入所有选项
             */
            q.setSurveyId(sid);
            questionMapper.insert(q);
            var qid = q.getQuestionId();

            for (var o : q.getOptionList()) {
                o.setQuestionId(qid);
                optionMapper.insert(o);
            }
        }
        return new CommonResult<>(survey);
    }

    @PostMapping("/UpdataSurvey")//修改问卷信息 输入完整问卷信息
    public CommonResult<Survey> UpdateSurvey(@RequestBody Survey survey) {
        var sid = survey.getSurveyId();
        if (surveyMapper.selectById(sid) == null) {
            return new CommonResult<>(400, "更新失败，问卷不存在");
        }
        DeleteSurveyById(survey);
        InsertSurvey(survey);
        Survey survey1 = surveyMapper.FindAllSurveyInfo(survey);
        return new CommonResult<>(survey1);
    }

    @PostMapping("/FindSurveyInfoById")//根据id查询问卷的所有问题和选项
    public CommonResult<Survey> FindSurveyInfoById(@RequestBody Survey survey) {
        var sid = survey.getSurveyId();
        if (surveyMapper.selectById(sid) == null) {
            return new CommonResult<>(400, "查询失败，问卷不存在");
        }
        return new CommonResult<>(surveyMapper.FindAllSurveyInfo(survey));
    }

    @PostMapping("/DeleteSurveyById")//根据问卷id删除整份问卷
    public CommonResult<Survey> DeleteSurveyById(@RequestBody Survey survey) {
        var sid = survey.getSurveyId();
        if (surveyMapper.selectById(sid) == null) {
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
        if (questionList1 != null) {
            for (var q1 : questionList1) {
                List<Option> optionList1 = q1.getOptionList();
                questionMapper.deleteById(q1);
                /*
                 * 删除原有问题选项
                 */
                if (optionList1 != null) {
                    for (var o1 : optionList1) {
                        optionMapper.deleteById(o1);
                    }
                }
            }
        }
        return new CommonResult<>(0, "删除成功");
    }

    @PostMapping("/UpdateSurveyState") //根据问卷id修改问卷状态
    public CommonResult<Survey> UpdateSurveyStatus(@RequestBody Survey survey) {
        var sid = survey.getSurveyId();
        if (surveyMapper.selectById(sid) == null) {
            return new CommonResult<>(400, "修改失败，问卷不存在");
        }
        surveyMapper.updateById(survey);
        return new CommonResult<>(survey);
    }

    @PostMapping("/GetSurveyAnalysis") //根据问卷id获取问卷分析
    public CommonResult<DataMessage> GetSurveyAnalysis(@RequestBody Survey survey) {
        String re = "";
        Survey survey1 = surveyMapper.FindAllSurveyInfo(survey);
        if (survey1 == null) {
            return new CommonResult<>(401, "获取失败");
        }
        List<Question> questionList = survey1.getQuestionList();
        if (questionList == null) {
            return new CommonResult<>(400, "获取失败");
        }
        for (var question : questionList) {
            if (question.getType() <= 2) {
                re = re + "{title:'" + question.getQuestion() + "',";
                var optionList = question.getOptionList();
                if (optionList == null) {
                    return new CommonResult<>(400, "获取失败");
                }
                re = re + "optionList: ";
                for (var option : question.getOptionList()) {
                    re = re + "{text:'" + option.getContent() + "'," + "fill_num:" + option.getCount() + "},";
                }
                re = re + "},";
            }
        }
        DataMessage dataMessage = new DataMessage();
        dataMessage.setPrompt(re);


        ChatGPT chatGPT = ChatGPT.builder()
                .timeout(600)
                .apiKey(apiKey)
                .proxy(proxy)
                .apiHost(apiHost)
                .build()
                .init();

        Message message = Message.of("给你一段json格式的数据，这是份问卷的数据，帮我分析下列给出的数据并且以自然语言去描述，具体分析下有多少人选了哪些选项，选项的占比是多少，并以此得出结论，以下是给出的数据：" + dataMessage.getPrompt());
        ChatCompletion chatCompletion = ChatCompletion.builder()
                .model(ChatCompletion.Model.GPT_3_5_TURBO.getName())
                .messages((List.of(message)))
                .maxTokens(3000)
                .temperature(0.9)
                .build();

        ChatCompletionResponse response = chatGPT.chatCompletion(chatCompletion);
        Message result = response.getChoices().get(0).getMessage();
        dataMessage.setPrompt(result.getContent());
        survey1.setAnalysis(result.getContent());
        surveyMapper.updateById(survey1);
        return new CommonResult<>(dataMessage);
    }
}