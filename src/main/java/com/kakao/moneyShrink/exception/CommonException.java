package com.kakao.moneyShrink.exception;

import com.kakao.moneyShrink.constant.Errors;
import lombok.Getter;

/**
 * Created by sangheon on 2020-11-21
 */
@Getter
public class CommonException extends RuntimeException {
    private Errors error;
    private String message;

    public CommonException(Errors e) {
        this.error = e;
        this.message = e.getMessage();
    }

    public CommonException(Errors e, String message) {
        this.error = e;
        this.message = message;
    }
}
