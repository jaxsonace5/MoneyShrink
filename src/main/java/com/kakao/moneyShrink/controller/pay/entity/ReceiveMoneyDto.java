package com.kakao.moneyShrink.controller.pay.entity;

import java.util.Date;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * Created by sangheon on 2020-11-21
 * desc : Receive Money Dto
 */
public class ReceiveMoneyDto {

    @Data
    public static class  ReceiveMoneyRequest{

        @NotEmpty(message = "토큰값은 필수입니다.")
        private String token;

        @NotNull(message = "유저 정보는 필수입니다.")
        private String userId;

        private Date requestDate;

    }

    @Data
    public static class  ReceiveMoneyResponse{

        private Integer amount;
        private Date receiveDate;
        private Integer ResultCode;
        private String ResultMessage;

    }
}
