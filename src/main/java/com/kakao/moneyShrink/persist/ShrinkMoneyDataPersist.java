package com.kakao.moneyShrink.persist;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kakao.moneyShrink.entity.ShrinkMoneyData;
import com.kakao.moneyShrink.repository.ShrinkMoneyDataRepository;

/**
 * Created by sangheon on 2020-11-21
 */
@Service
@Transactional
public class ShrinkMoneyDataPersist {
    @Autowired
    private ShrinkMoneyDataRepository shrinkMoneyDataRepository;

    /**
     * 뿌리기 저장
     */
    public void createShrinkMoneyData(ShrinkMoneyData data) {
        this.shrinkMoneyDataRepository.save(data);
    }

    /**
     * 뿌리기 조회
     */
    public ShrinkMoneyData readShrinkMoneyData(String roomId, String token) {
        return this.shrinkMoneyDataRepository.findByRoomIdAndToken(roomId, token);
    }

}
