package com.example.surveysystemtext.controller;

import com.example.surveysystemtext.entity.DataMessage;
import com.plexpt.chatgpt.ChatGPT;
import com.plexpt.chatgpt.entity.chat.ChatCompletion;
import com.plexpt.chatgpt.entity.chat.ChatCompletionResponse;
import com.plexpt.chatgpt.entity.chat.Message;
import com.plexpt.chatgpt.util.Proxys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.net.Proxy;
import java.util.List;

@RestController
public class ChatController {
    Proxy proxy = Proxys.http("127.0.0.1", 7890);
    @Value("${chatgpt.apiKey}")
    private String apiKey;
    @Value("${chatgpt.apiHost}")
    private String apiHost;

    @PostMapping("/generate")
    public Message generate(@RequestBody DataMessage dataMessage) {

        ChatGPT chatGPT = ChatGPT.builder()
                .timeout(600)
                .apiKey(apiKey)
                .proxy(proxy)
                .apiHost(apiHost)
                .build()
                .init();

        Message system = Message.ofSystem("""
                请你根据我输入给你的问卷主题随机生成一份调查问卷，其中包括问卷的所有题目，问卷中只有选择题，题目数量输出2题，并用JSON的方式完成输入输出，使用下面的格式完成输入输出：


                输入:
                {
                "keyword": "大学生就业情况调查"
                }


                输出:
                [
                {
                "title": "您目前就读的专业属于以下哪种类型？",
                "optionList": [
                {
                "text": "理工科"
                },
                {
                "text": "文史类"
                },
                {
                "text": "医学类"
                },
                {
                "text": "艺术类"
                },
                {
                "text": "其他"
                }
                ]
                },
                {
                "title": "您较为关心以下哪方面的就业问题？",
                "optionList": [
                {
                "text": "行业前景"
                },
                {
                "text": "职业发展"
                },
                {
                "text": "薪资待遇"
                },
                {
                "text": "工作稳定性"
                },
                {
                "text": "其他"
                }
                ]
                },
                {
                "title": "您对以下哪种职业类型最感兴趣？",
                "optionList": [
                {
                "text": "互联网/IT"
                },
                {
                "text": "金融/投资"
                },
                {
                "text": "教育/科研"
                },
                {
                "text": "艺术/传媒"
                },
                {
                "text": "医疗/生物"
                },
                {
                "text": "公务员/事业单位"
                },
                {
                "text": "其他"
                }
                ]
                },
                {
                "title": "以下哪种求职方式您更倾向于采用？",
                "optionList": [
                {
                "text": "网络招聘"
                },
                {
                "text": "校园招聘"
                },
                {
                "text": "猎头/中介"
                },
                {
                "text": "自主求职"
                },
                {
                "text": "其他"
                }
                ]
                },
                {
                "title": "您对于未来几年国内经济发展形势的预期是？",
                "optionList": [
                {
                "text": "十分乐观"
                },
                {
                "text": "比较乐观"
                },
                {
                "text": "持中立态度"
                },
                {
                "text": "比较悲观"
                },
                {
                "text": "十分悲观"
                }
                ]
                }
                ]""");
        Message message = Message.of(dataMessage.getPrompt());
        ChatCompletion chatCompletion = ChatCompletion.builder()
                .model(ChatCompletion.Model.GPT_3_5_TURBO.getName())
                .messages((List.of(system, message)))
                .maxTokens(3000)
                .temperature(0.9)
                .build();

        ChatCompletionResponse response = chatGPT.chatCompletion(chatCompletion);
        Message result = response.getChoices().get(0).getMessage();
        return result;
    }

    @PostMapping("/description")
    public Message description(@RequestBody DataMessage dataMessage) {
        ChatGPT chatGPT = ChatGPT.builder()
                .timeout(600)
                .apiKey(apiKey)
                .proxy(proxy)
                .apiHost(apiHost)
                .build()
                .init();

        Message message = Message.of("帮我分析下列给出的数据" + dataMessage.getPrompt());
        ChatCompletion chatCompletion = ChatCompletion.builder()
                .model(ChatCompletion.Model.GPT_3_5_TURBO.getName())
                .messages((List.of(message)))
                .maxTokens(3000)
                .temperature(0.9)
                .build();

        ChatCompletionResponse response = chatGPT.chatCompletion(chatCompletion);
        Message result = response.getChoices().get(0).getMessage();
        return result;
    }
}
