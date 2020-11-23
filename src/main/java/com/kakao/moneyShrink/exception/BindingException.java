package com.kakao.moneyShrink.exception;

import org.springframework.validation.FieldError;

/**
 * Created by sangheon on 2020-11-21
 */
public class BindingException extends RuntimeException {
    private FieldError fieldError;

    public BindingException(FieldError error) {
        this.fieldError = error;
    }

    public FieldError getFieldError() {
        return fieldError;
    }
}
