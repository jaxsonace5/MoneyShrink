package com.kakao.moneyShrink.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kakao.moneyShrink.entity.ShrinkMoneyData;

/**
 * Created by sangheon on 2020-11-21
 */
public interface ShrinkMoneyDataRepository extends JpaRepository<ShrinkMoneyData, Long> {
    ShrinkMoneyData findByRoomIdAndToken(String roomId, String token);
}
