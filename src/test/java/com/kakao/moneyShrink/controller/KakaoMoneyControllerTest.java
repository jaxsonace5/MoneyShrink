package com.kakao.moneyShrink.controller;

import com.kakao.moneyShrink.controller.pay.entity.ReceiveMoneyDto.ReceiveMoneyRequest;
import com.kakao.moneyShrink.controller.pay.entity.ReceiveMoneyDto.ReceiveMoneyResponse;
import com.kakao.moneyShrink.controller.pay.entity.SearchMoneyDto.SearchMoneyRequest;
import com.kakao.moneyShrink.controller.pay.entity.SearchMoneyDto.SearchMoneyResponse;
import com.kakao.moneyShrink.controller.pay.entity.ShrinkMoneyDto.ShrinkMoneyRequest;
import com.kakao.moneyShrink.controller.pay.entity.ShrinkMoneyDto.ShrinkMoneyResponse;
import com.kakao.moneyShrink.util.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

/**
 * Created by sangheon on 2020-10-13
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class KakaoMoneyControllerTest {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${url.pay}")
    private String payUrl;

    @Test
    public void shrinkMoney() {

        ShrinkMoneyRequest shrinkMoneyRequest = new ShrinkMoneyRequest();

        Integer ShrinkAmount = 120000;
        Integer userCount = 5;
        String userId = "sangheon";
        String roomId = "room1";

        shrinkMoneyRequest.setShrinkAmount(ShrinkAmount);
        shrinkMoneyRequest.setUserCount(userCount);

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.set("Content-type", "application/json");
        headers.set("X-USER-ID", userId);
        headers.set("X-ROOM-ID", roomId);
        HttpEntity<ShrinkMoneyRequest> httpEntity = new HttpEntity<>(shrinkMoneyRequest, headers);

        URI uri = UriComponentsBuilder.fromUriString(payUrl + "shrink").build().toUri();
        ResponseEntity<ShrinkMoneyResponse> responseEntity
                = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, ShrinkMoneyResponse.class);

        System.out.println(responseEntity.getBody());

    }

    @Test
    public void receiveMoney() {

        ReceiveMoneyRequest receiveMoneyRequest = new ReceiveMoneyRequest();

        String userId = "hongkildong";
        String roomId = "room1";
        String token = "YDP";

        receiveMoneyRequest.setToken(token);
        receiveMoneyRequest.setUserId(userId);

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.set("Content-type", "application/json");
        headers.set("X-USER-ID", userId);
        headers.set("X-ROOM-ID", roomId);
        HttpEntity<ReceiveMoneyRequest> httpEntity = new HttpEntity<>(receiveMoneyRequest, headers);

        URI uri = UriComponentsBuilder.fromUriString(payUrl + "receive").build().toUri();
        ResponseEntity<ReceiveMoneyResponse> responseEntity
                = restTemplate.exchange(uri, HttpMethod.PUT, httpEntity, ReceiveMoneyResponse.class);

        System.out.println(responseEntity.getBody());

    }

    @Test
    public void searchMoney() {

        SearchMoneyRequest searchMoneyRequest = new SearchMoneyRequest();

        String userId = "sangheon";
        String roomId = "room1";
        String token = "YDP";

        searchMoneyRequest.setToken(token);

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.set("Content-type", "application/json");
        headers.set("X-USER-ID", userId);
        headers.set("X-ROOM-ID", roomId);
        HttpEntity<SearchMoneyRequest> httpEntity = new HttpEntity<>(searchMoneyRequest, headers);

        URI uri = UriComponentsBuilder.fromUriString(payUrl + "search").build().toUri();
        ResponseEntity<SearchMoneyResponse> responseEntity
                = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, SearchMoneyResponse.class);

        System.out.println(responseEntity.getBody());

    }

    @Test
    public void getToken() {
        String token = CommonUtils.getToken();

        System.out.println(token);
    }

}
