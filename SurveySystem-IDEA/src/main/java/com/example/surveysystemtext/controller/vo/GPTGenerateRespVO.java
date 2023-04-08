package com.example.surveysystemtext.controller.vo;

import lombok.Data;

import java.util.List;

@Data
public class GPTGenerateRespVO {
    private List<Question> questionList;

    @Data
    public static class Question {
        private String title;
        private List<Option> optionList;

        // 处理getter/setter方法

        @Data
        public static class Option {
            private String text;

            // 处理getter/setter方法
        }
    }
}

