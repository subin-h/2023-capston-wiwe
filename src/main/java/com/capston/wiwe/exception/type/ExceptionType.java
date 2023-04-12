package com.capston.wiwe.exception.type;

import lombok.Getter;

@Getter
public class ExceptionType {
    private final String code;
    private final String message;

    ExceptionType(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
