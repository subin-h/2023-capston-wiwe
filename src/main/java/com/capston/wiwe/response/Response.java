package com.capston.wiwe.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class Response {

    private boolean success;
    private int code;
    private Result result;


    public static <T> Response success(T data) {
        return new Response(true, 0, new Success<>(data,"ok"));
    }


    public static Response failure(int code, String msg) {
        return new Response(false, code, new Failure(null,msg));
    }
}