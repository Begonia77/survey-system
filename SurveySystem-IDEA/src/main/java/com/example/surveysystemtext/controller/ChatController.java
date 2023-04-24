package com.example.surveysystemtext.controller;

import com.example.surveysystemtext.controller.vo.GPTDescripeVO;
import com.example.surveysystemtext.controller.vo.GPTGenerateRespVO;
import com.example.surveysystemtext.entity.DataMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.plexpt.chatgpt.ChatGPT;
import com.plexpt.chatgpt.entity.chat.ChatCompletion;
import com.plexpt.chatgpt.entity.chat.ChatCompletionResponse;
import com.plexpt.chatgpt.entity.chat.Message;
import com.plexpt.chatgpt.util.Proxys;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.net.Proxy;
import java.util.List;

@RestController
@CrossOrigin
public class ChatController {
    Proxy proxy = Proxys.http("localhost", 7890);
    @Value("${chatgpt.apiKey}")
    private String apiKey;
    @Value("${chatgpt.apiHost}")
    private String apiHost;

    @PostMapping("/generate")
    @SneakyThrows
    public GPTGenerateRespVO generate(@RequestBody DataMessage dataMessage) {

        ChatGPT chatGPT = ChatGPT.builder()
                .timeout(120)
                .apiKey(apiKey)
                .proxy(proxy)
                .apiHost(apiHost)
                .build()
                .init();

        Message system = Message.ofSystem(String.format("""
                请你根据我输入给你的问卷主题随机生成一份调查问卷，其中包括问卷的所有题目，问卷中只有选择题，题目数量输出2题，并用JSON的方式完成输入输出，使用下面的格式完成输入输出：
                输入:
                 {"keyword": "大学生就业情况调查"}
                输出:
                 {"questionList":[{"title":"目前就读的专业类型","optionList":[{"text":"理工科"},{"text":"文史类"},{"text":"医学类"},{"text":"艺术类"},{"text":"其他"}]}]}
                 
                输入:
                 {"keyword": "%s"}
                输出:
                 """, dataMessage.getPrompt()));
        ChatCompletion chatCompletion = ChatCompletion.builder()
                .model(ChatCompletion.Model.GPT_3_5_TURBO.getName())
                .messages(List.of(system))
                .maxTokens(3000)
                .temperature(0.9)
                .build();

        ChatCompletionResponse response = chatGPT.chatCompletion(chatCompletion);
        Message result = response.getChoices().get(0).getMessage();
        ObjectMapper mapper = new ObjectMapper();
        GPTGenerateRespVO respVO;
        try {
            respVO = mapper.readValue(result.getContent(), GPTGenerateRespVO.class);
        } catch (Exception e) {
            System.out.println("解析失败");
            e.printStackTrace();
            return new GPTGenerateRespVO();
        }
        return respVO;
    }

//    @PostMapping("/description")
//    public GPTDescripeVO description(@RequestBody DataMessage dataMessage) {
//        ChatGPT chatGPT = ChatGPT.builder()
//                .timeout(600)
//                .apiKey(apiKey)
//                .proxy(proxy)
//                .apiHost(apiHost)
//                .build()
//                .init();
//
//        Message message = Message.of("帮我分析下列给出的数据" + dataMessage.getPrompt());
//        ChatCompletion chatCompletion = ChatCompletion.builder()
//                .model(ChatCompletion.Model.GPT_3_5_TURBO.getName())
//                .messages((List.of(message)))
//                .maxTokens(3000)
//                .temperature(0.9)
//                .build();
//
//        ChatCompletionResponse response = chatGPT.chatCompletion(chatCompletion);
//        Message result = response.getChoices().get(0).getMessage();
//        GPTDescripeVO respVO = new GPTDescripeVO();
//        respVO.setContent(result.getContent());
//        return respVO;
//    }
}
