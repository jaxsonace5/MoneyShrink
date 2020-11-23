package com.kakao.moneyShrink.exception;

import com.kakao.moneyShrink.constant.Errors;
import lombok.Data;

/**
 * Created by sangheon on 2020-11-21
 */
@Data
public class ExceptionResponse {
    private int status;
    private String message;

    public ExceptionResponse() {
    }

    public ExceptionResponse(Errors e) {
        this.status = e.getCode();
        this.message = e.getMessage();
    }
}
