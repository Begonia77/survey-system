package com.example.surveysystemtext.entity;

import lombok.Data;

@Data
public class CommonResult<T>  {
    private T data;
    private Integer code = 0;
    private String message = "";
    private CommonResult(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private CommonResult(T data) {
        this.data = data;
    }
}