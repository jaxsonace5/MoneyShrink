package com.kakao.moneyShrink.constant;

import lombok.Getter;

/**
 * Created by sangheon on 2020-11-21
 */
@Getter
public enum  Errors {
    GENERAL_UNKNOWN(-1000, "알수 없는 에러가 발생"),
    GENERAL_INVALID_HEADER(-1001, "header 정보가 올바르지 않습니다."),
    GENERAL_INVALID_BODY(-1002, "body 정보가 올바르지 않습니다."),
    GENERAL_INVALID_PARAM(-1003, "param 정보가 올바르지 않습니다."),

    SHRINK_DUPLICATE(-1003, "동일한 뿌리기 데이터가 이미 존재합니다."),
    SHRINK_NONE(-1004, "뿌리기 데이터가 존재하지 않거나 방이 다릅니다."),
    SHRINK_USER_EQUAL_RECEIVE_USER(-1005, "뿌린 사람이 받을 수 없습니다."),
    SHRINK_EXPIRE(-1006, "뿌리기 받기 시간이 만료되었습니다."),
    SHRINK_COMPLETE(-1007, "아쉽게도 뿌린 금액을 이미 모두 받았습니다."),
    SHRINK_EXCESS(-1008, "할당 된 인원 수를 초과하였습니다."),
    SHRINK_ALREADY_RECEIVED_USER(-1009, "이미 뿌린 금액 받은 사용자입니다."),
    SHRINK_SEARCH_AUTH_CHECK(-1010, "뿌린 당사자만 조회가 가능합니다.");

    private int code;
    private String message;

    Errors(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
