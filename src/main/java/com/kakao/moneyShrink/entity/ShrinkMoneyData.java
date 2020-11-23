package com.kakao.moneyShrink.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

import lombok.Data;
import lombok.Getter;

/**
 * Created by sangheon on 2020-11-21
 */
@Data
@Entity
@IdClass(ShrinkKey.class)
public class ShrinkMoneyData implements Serializable {

    @Id
    private String roomId;  // rood Id

    @Id
    private String token;   // 토큰 값

    private Integer ShrinkAmount;  // 전체 뿌린 금액
    private Integer personAmount;  // 인당 받을 금액
    private String shrinkId;  // 뿌린자 Id
    private Date validDate;   // 유효일자
    private Integer userCount;  // 뿌릴 대상 카운트
    private Integer status; // 상태
    private Date regDate;   // 등록일시
    private String regId; // 등록자
    private Date updDate;   // 수정일시
    private String updId; // 수정자

    @Getter
    public enum StatusType {

        INIT(0, "최초 뿌리기"),
        RECEIVING(1, "받기 진행중"),
        COMPLETE(2, "받기 모두 완료");

        private Integer statusType;
        private String statusValue;

        StatusType(Integer statusType, String statusValue) {
            this.statusType = statusType;
            this.statusValue = statusValue;
        }
    }
}
