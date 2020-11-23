package com.kakao.moneyShrink.controller.pay;

import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.kakao.moneyShrink.controller.pay.entity.SearchMoneyDto.SearchMoneyRequest;
import com.kakao.moneyShrink.controller.pay.entity.SearchMoneyDto.SearchMoneyResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kakao.moneyShrink.controller.pay.entity.ReceiveMoneyDto.ReceiveMoneyRequest;
import com.kakao.moneyShrink.controller.pay.entity.ReceiveMoneyDto.ReceiveMoneyResponse;
import com.kakao.moneyShrink.controller.pay.entity.SearchMoneyDto.SearchMoneyRequest;
import com.kakao.moneyShrink.controller.pay.entity.ShrinkMoneyDto.ShrinkMoneyResponse;
import com.kakao.moneyShrink.exception.BindingException;
import com.kakao.moneyShrink.controller.pay.entity.ShrinkMoneyDto.ShrinkMoneyRequest;
import com.kakao.moneyShrink.service.pay.KakaoPayService;

/**
 * Created by sangheon on 2020-11-21
 */
@RestController
@RequestMapping("/pay")
public class KakaoPayController {

    @Autowired
    private KakaoPayService kakaoPayService;

    /**
     * 머니 뿌리기
     */
    @PostMapping("/v1/shrink")
    public ResponseEntity shrinkMoney(HttpServletRequest request,
        @Valid @RequestBody ShrinkMoneyRequest body, BindingResult result) {

        if (result.hasErrors()) {
            throw new BindingException(result.getFieldError());
        }

        ShrinkMoneyResponse response = this.kakaoPayService.shrinkMoney(request, body);

        return new ResponseEntity(response, HttpStatus.CREATED);
    }

    /**
     * 머니 받기
     */
    @PutMapping("/v1/receive")
    public ResponseEntity receiveMoney(HttpServletRequest request,
        @Valid @RequestBody ReceiveMoneyRequest body, BindingResult result) {

        Date requestDate = new Date();
        body.setRequestDate(requestDate);

        if (result.hasErrors()) {
            throw new BindingException(result.getFieldError());
        }

        ReceiveMoneyResponse response = this.kakaoPayService.receiveMoney(request, body);

        return new ResponseEntity(response, HttpStatus.OK);
    }

    /**
     * 머니 조회
     */
    @PostMapping("/v1/search")
    public ResponseEntity cancelCoupon(HttpServletRequest request,
        @Valid @RequestBody SearchMoneyRequest body, BindingResult result) {

        if (result.hasErrors()) {
            throw new BindingException(result.getFieldError());
        }

        SearchMoneyResponse response = this.kakaoPayService.searchMoney(request, body);

        return new ResponseEntity(response, HttpStatus.OK);
    }
}

