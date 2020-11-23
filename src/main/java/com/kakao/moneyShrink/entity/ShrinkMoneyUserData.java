package com.kakao.moneyShrink.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

import lombok.Data;

/**
 * Created by sangheon on 2020-11-21
 */
@Data
@Entity
@IdClass(ShrinkUserKey.class)
public class ShrinkMoneyUserData implements Serializable {

    @Id
    private String roomId;  // rood Id

    @Id
    private String token;   // 토큰 값

    @Id
    private String userId;   // 유저 Id

    private Integer ReceivedAmount;  // 받은 금액
    private Date regDate;   // 등록일시
    private String regId; // 등록자

}
