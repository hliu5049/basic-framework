package com.framework.ommon;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response<T> implements Serializable {
    private static final Integer SUCCESS = 200;
    private static final String SUCCESS_MSG = "成功";

    private static final Integer FAILED = 500;
    private static final String FAILED_MSG = "失败";
    private Integer code;
    private String message;
    private T data;

    public static <T> Response<T> success() {
        return new Response<>(SUCCESS, SUCCESS_MSG, null);
    }

    public static <T> Response<T> success(T data) {
        return new Response<>(SUCCESS, SUCCESS_MSG, data);
    }

    public static <T> Response<T> failed(String msg) {
        return new Response<>(FAILED, msg, null);
    }

    public static <T> Response<T> failed(Integer errorCode, String errorMsg) {
        return new Response<>(errorCode, errorMsg, null);
    }

    public static <T> Response<T> failed(Integer errorCode, String errorMsg, T data) {
        return new Response<>(errorCode, errorMsg, data);
    }

}
