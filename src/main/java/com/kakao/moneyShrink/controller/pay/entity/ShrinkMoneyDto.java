package com.kakao.moneyShrink.controller.pay.entity;

import java.util.Date;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * Created by sangheon on 2020-11-21
 * desc : Shrink Money Dto
 */
public class ShrinkMoneyDto {

    @Data
    public static class ShrinkMoneyRequest {

        @NotNull(message = "뿌릴 금액은 필수입니다.")
        @Min(1000)
        private Integer shrinkAmount;

        @NotNull(message = "뿌릴 인원은 필수입니다.")
        private Integer userCount;

    }

    @Data
    public static class ShrinkMoneyResponse {

        private String token;
        private Integer shrinkAmount;
        private Integer personAmount;
        private Integer userCount;
        private Date validDate;

    }
}
