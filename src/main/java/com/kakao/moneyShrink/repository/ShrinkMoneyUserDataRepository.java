package com.kakao.moneyShrink.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.kakao.moneyShrink.entity.ShrinkMoneyUserData;

/**
 * Created by sangheon on 2020-11-21
 */
public interface ShrinkMoneyUserDataRepository extends JpaRepository<ShrinkMoneyUserData, Long> {

    List<ShrinkMoneyUserData> findByRoomIdAndToken(String roomId, String token);

    ShrinkMoneyUserData findByRoomIdAndTokenAndUserId(String roomId, String token, String userId);

}
