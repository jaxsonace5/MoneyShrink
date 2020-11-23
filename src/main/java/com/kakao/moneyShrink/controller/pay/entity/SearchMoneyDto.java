package com.kakao.moneyShrink.controller.pay.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.validation.constraints.NotEmpty;

import lombok.Data;

/**
 * Created by sangheon on 2020-11-21
 * desc : Search Money Dto
 */
public class SearchMoneyDto {

    @Data
    public static class SearchMoneyRequest {

        @NotEmpty(message = "토큰값은 필수입니다.")
        private String token;
    }

    @Data
    public static class SearchMoneyResponse {

        private String token;
        private Date shrinkDate;
        private Integer shrinkAmount;
        private Integer receivedAmount;
        private List<ReceivedUserInfo> userInfoList = new ArrayList<>();

        @Data
        public static class ReceivedUserInfo {
            private String userId;
            private Integer receivedAmount;
        }
    }
}
