package com.example.surveysystemtext.entity;

import lombok.Data;

@Data
public class CommonResult<T>  {
    private T data;
    private Integer code = 0;
    private String message = "";
     public CommonResult(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public CommonResult(T data) {
        this.data = data;
    }
}