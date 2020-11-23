package com.kakao.moneyShrink.persist;

import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kakao.moneyShrink.entity.ShrinkMoneyUserData;
import com.kakao.moneyShrink.repository.ShrinkMoneyUserDataRepository;

/**
 * Created by sangheon on 2020-11-21
 */
@Service
@Transactional
public class ShrinkMoneyUserDataPersist {
    @Autowired
    private ShrinkMoneyUserDataRepository shrinkMoneyUserDataRepository;

    /**
     * 받기 유저 저장
     */
    public void createShrinkMoneyUserData(ShrinkMoneyUserData data) {
        this.shrinkMoneyUserDataRepository.save(data);
    }

    /**
     * 받기 유저 목록 조회
     */
    public List<ShrinkMoneyUserData> readShrinkMoneyUserData(String roomId, String token) {
        return this.shrinkMoneyUserDataRepository.findByRoomIdAndToken(roomId, token);
    }

    /**
     * 받기 유저 상세 조회
     */
    public ShrinkMoneyUserData readShrinkMoneyUserData(String roomId, String token, String userId) {
        return this.shrinkMoneyUserDataRepository.findByRoomIdAndTokenAndUserId(roomId, token, userId);
    }
}
