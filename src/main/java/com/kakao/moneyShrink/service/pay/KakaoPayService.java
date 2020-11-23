package com.kakao.moneyShrink.service.pay;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import com.kakao.moneyShrink.constant.GlobalConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.kakao.moneyShrink.constant.Errors;
import com.kakao.moneyShrink.controller.pay.entity.ReceiveMoneyDto.ReceiveMoneyRequest;
import com.kakao.moneyShrink.controller.pay.entity.ReceiveMoneyDto.ReceiveMoneyResponse;
import com.kakao.moneyShrink.controller.pay.entity.SearchMoneyDto.SearchMoneyRequest;
import com.kakao.moneyShrink.controller.pay.entity.SearchMoneyDto.SearchMoneyResponse;
import com.kakao.moneyShrink.controller.pay.entity.SearchMoneyDto.SearchMoneyResponse.ReceivedUserInfo;
import com.kakao.moneyShrink.controller.pay.entity.ShrinkMoneyDto.ShrinkMoneyRequest;
import com.kakao.moneyShrink.controller.pay.entity.ShrinkMoneyDto.ShrinkMoneyResponse;
import com.kakao.moneyShrink.entity.ShrinkMoneyData;
import com.kakao.moneyShrink.entity.ShrinkMoneyData.StatusType;
import com.kakao.moneyShrink.entity.ShrinkMoneyUserData;
import com.kakao.moneyShrink.exception.CommonException;
import com.kakao.moneyShrink.persist.ShrinkMoneyDataPersist;
import com.kakao.moneyShrink.persist.ShrinkMoneyUserDataPersist;
import com.kakao.moneyShrink.util.CommonUtils;

@Service
@Transactional
public class KakaoPayService {

    @Autowired
    private ShrinkMoneyDataPersist shrinkMoneyDataPersist;

    @Autowired
    private ShrinkMoneyUserDataPersist shrinkMoneyUserDataPersist;

    /**
     * 머니 뿌리기
     */
    public ShrinkMoneyResponse shrinkMoney(HttpServletRequest request, ShrinkMoneyRequest body) {

        ShrinkMoneyResponse responseBody = new ShrinkMoneyResponse();

        String roomId = request.getHeader("X-ROOM-ID");
        String shrinkId = request.getHeader("X-USER-ID");
        Integer shrinkAmount = body.getShrinkAmount();
        Integer userCount = body.getUserCount();

        String token = CommonUtils.getToken();

        // 기존에 동일한 뿌리기가 존재하는지 체크
        ShrinkMoneyData shrinkMoneyData = shrinkMoneyDataPersist.readShrinkMoneyData(roomId, token);

        if (!ObjectUtils.isEmpty(shrinkMoneyData)) {
            throw new CommonException(Errors.SHRINK_DUPLICATE);
        }

        shrinkMoneyData = new ShrinkMoneyData();
        Date date = new Date();

        shrinkMoneyData.setShrinkId(shrinkId);
        shrinkMoneyData.setRoomId(roomId);
        shrinkMoneyData.setToken(token);
        shrinkMoneyData.setShrinkAmount(shrinkAmount);
        shrinkMoneyData.setUserCount(body.getUserCount());
        shrinkMoneyData.setRegId(shrinkId);
        shrinkMoneyData.setStatus(StatusType.INIT.getStatusType());
        shrinkMoneyData.setRegDate(date);

        Integer validMinute = GlobalConstants.SHRINK_RECEIVE_VALID_MINUTE;

        Date validDate = CommonUtils.addDateMinute(date, validMinute);
        shrinkMoneyData.setValidDate(validDate);

        Integer personAmount = (shrinkAmount / userCount);
        shrinkMoneyData.setPersonAmount(personAmount);

        shrinkMoneyDataPersist.createShrinkMoneyData(shrinkMoneyData);

        responseBody.setToken(token);
        responseBody.setShrinkAmount(shrinkAmount);
        responseBody.setPersonAmount(personAmount);
        responseBody.setValidDate(validDate);

        return responseBody;

    }

    /**
     * 머니 받기
     */
    public ReceiveMoneyResponse receiveMoney(HttpServletRequest request, ReceiveMoneyRequest body) {

        ReceiveMoneyResponse responseBody = new ReceiveMoneyResponse();

        String roomId = request.getHeader("X-ROOM-ID");
        String userId = request.getHeader("X-USER-ID");
        String token = body.getToken();

        ShrinkMoneyData shrinkMoneyData = shrinkMoneyDataPersist.readShrinkMoneyData(roomId, token);

        // 뿌리기가 존재하는지 확인
        if (ObjectUtils.isEmpty(shrinkMoneyData)) {
            throw new CommonException(Errors.SHRINK_NONE);
        }

        String shrinkId = shrinkMoneyData.getShrinkId();

        // 뿌린 사람이 받기를 요청했는지 확인
        if (shrinkId.equals(userId)) {
            throw new CommonException(Errors.SHRINK_USER_EQUAL_RECEIVE_USER);
        }


        Date vailDate = shrinkMoneyData.getValidDate();
        Date requestDate = body.getRequestDate();

        // 유효시간이 지났는지 확인
        if (vailDate.before(requestDate)) {
            throw new CommonException(Errors.SHRINK_EXPIRE);
        }

        Integer status = shrinkMoneyData.getStatus();

        // 뿌리기 모두 받았는지 확인
        if (status.equals(StatusType.COMPLETE.getStatusType())) {
            throw new CommonException(Errors.SHRINK_COMPLETE);
        }

        ShrinkMoneyUserData receivedUser =  shrinkMoneyUserDataPersist.readShrinkMoneyUserData(roomId, token, userId);

        // 이미 받은 유저인지 확인
        if (!ObjectUtils.isEmpty(receivedUser)) {
            throw new CommonException(Errors.SHRINK_ALREADY_RECEIVED_USER);
        }

        ShrinkMoneyUserData userData = new ShrinkMoneyUserData();
        Date receiveDate = new Date();
        Integer receiveAmount = shrinkMoneyData.getPersonAmount();
        userData.setReceivedAmount(receiveAmount);
        userData.setRegDate(receiveDate);
        userData.setRegId(userId);
        userData.setRoomId(roomId);
        userData.setToken(token);
        userData.setUserId(userId);

        shrinkMoneyUserDataPersist.createShrinkMoneyUserData(userData);

        List<ShrinkMoneyUserData> receivedUserList = shrinkMoneyUserDataPersist.readShrinkMoneyUserData(roomId, token);

        if (receivedUserList.size() == shrinkMoneyData.getUserCount()) {
            status = StatusType.COMPLETE.getStatusType();
        } else if (receivedUserList.size() < shrinkMoneyData.getUserCount()) {
            status = StatusType.RECEIVING.getStatusType();
        } else {
            throw new CommonException(Errors.SHRINK_EXCESS);
        }

        shrinkMoneyData.setStatus(status);
        shrinkMoneyData.setUpdDate(receiveDate);
        shrinkMoneyData.setUpdId(userId);

        responseBody.setAmount(receiveAmount);
        responseBody.setReceiveDate(receiveDate);

        return responseBody;

    }

    /**
     * 머니 조회
     */
    public SearchMoneyResponse searchMoney(HttpServletRequest request, SearchMoneyRequest body) {

        SearchMoneyResponse responseBody = new SearchMoneyResponse();
        String roomId = request.getHeader("X-ROOM-ID");
        String userId = request.getHeader("X-USER-ID");
        String token = body.getToken();

        ShrinkMoneyData shrinkMoneyData = shrinkMoneyDataPersist.readShrinkMoneyData(roomId, token);

        if (!userId.equals(shrinkMoneyData.getShrinkId())) {
            throw new CommonException(Errors.SHRINK_SEARCH_AUTH_CHECK);
        }

        List<ShrinkMoneyUserData> receivedUserList = shrinkMoneyUserDataPersist.readShrinkMoneyUserData(roomId, token);
        List<ReceivedUserInfo> userInfoList = new ArrayList<>();

        Integer receivedAmount = 0;

        for (ShrinkMoneyUserData userData : receivedUserList) {

            ReceivedUserInfo receivedUserInfo = new ReceivedUserInfo();
            receivedUserInfo.setReceivedAmount(userData.getReceivedAmount());
            receivedUserInfo.setUserId(userData.getUserId());
            userInfoList.add(receivedUserInfo);
            receivedAmount += userData.getReceivedAmount();
        }

        responseBody.setReceivedAmount(receivedAmount);
        responseBody.setShrinkAmount(shrinkMoneyData.getShrinkAmount());
        responseBody.setUserInfoList(userInfoList);

        return responseBody;

    }

}
