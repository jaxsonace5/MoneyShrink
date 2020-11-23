package com.kakao.moneyShrink.controller;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import com.kakao.moneyShrink.constant.Errors;
import com.kakao.moneyShrink.exception.BindingException;
import com.kakao.moneyShrink.exception.CommonException;
import com.kakao.moneyShrink.exception.ContentNotFoundException;
import org.slf4j.MDC;

import com.kakao.moneyShrink.exception.ExceptionResponse;

/**
 * Created by sangheon on 2020-11-21
 */
@ControllerAdvice
@RestController
public class ExceptionAdvice implements ErrorController {

    @ExceptionHandler(Exception.class)
    public ResponseEntity exception(HttpServletRequest request, Exception e) {

        ExceptionResponse response = new ExceptionResponse();
        response.setStatus(Errors.GENERAL_UNKNOWN.getCode());
        response.setMessage(e.getClass().getName());

        request.setAttribute("error", e);

        return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity notReadableException(HttpServletRequest request, HttpMessageNotReadableException e) {
        ExceptionResponse response = new ExceptionResponse();
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setMessage(HttpStatus.BAD_REQUEST.getReasonPhrase());

        request.setAttribute("warn", e);
        request.setAttribute("warn-response", response);

        return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CommonException.class)
    public ResponseEntity commonException(HttpServletRequest request, CommonException e) {
        ExceptionResponse response = new ExceptionResponse(e.getError());
        response.setStatus(e.getError().getCode());
        response.setMessage(e.getMessage());

        request.setAttribute("warn", e);
        request.setAttribute("warn-response", response);

        return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = BindingException.class)
    public ResponseEntity bindingException(HttpServletRequest request, BindingException e) {
        FieldError fe = e.getFieldError();

        ExceptionResponse response = new ExceptionResponse();
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setMessage("[" + fe.getField() + "] " + fe.getDefaultMessage());

        request.setAttribute("warn", e);
        request.setAttribute("warn-response", response);

        return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ContentNotFoundException.class)
    public ResponseEntity notFound(HttpServletRequest request, ContentNotFoundException e) {

        request.setAttribute("warn", e);

        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    private static final String PATH = "/error";
    @Autowired
    private ErrorAttributes errorAttributes;

    @Override
    public String getErrorPath() {
        return PATH;
    }

    /**
     * default error 처리.
     */
    @RequestMapping(value = PATH)
    public ExceptionResponse error(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> error = errorAttributes.getErrorAttributes(new ServletWebRequest(request), false);
        ExceptionResponse errorResponse = new ExceptionResponse();
        errorResponse.setStatus((Integer) error.get("status"));
        errorResponse.setMessage(error.get("error") + " (" + error.get("path") + ")");

        MDC.put("http-request-uri", (String) error.get("path"));

        return errorResponse;
    }
}
