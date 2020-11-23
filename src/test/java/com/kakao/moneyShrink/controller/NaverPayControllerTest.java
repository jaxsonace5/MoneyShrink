package com.kakao.moneyShrink.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by sangheon on 2020-10-13
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class NaverPayControllerTest {
    @Autowired
    private WebApplicationContext wac;

    @Test
    public void NaverSettlementRequestInfo() throws JsonProcessingException {

        System.out.println("11111");

        // NaverSettlementRequest.RequestReqDto requestReqDto = new NaverSettlementRequest.RequestReqDto();
        //
        // requestReqDto.setBusinessNo("1388190354");
        // requestReqDto.setRequestDate("2020-10-14");
        //
        // List<DifferenceRequest> differenceRequests = new ArrayList<>();
        //
        // DifferenceRequest differenceRequest = new DifferenceRequest();
        // differenceRequest.setAmount(10000L);
        // differenceRequest.setCancel(false);
        // differenceRequest.setPayHistId("20201014NP1001526931");
        // differenceRequest.setSequence("00");
        // differenceRequest.setTradeDate("2020-10-14");
        //
        // differenceRequests.add(differenceRequest);
        //
        // List<SubItem> subItems = new ArrayList<>();
        // SubItem subItem = new SubItem();
        // subItem.setOrderNo("001");
        // subItem.setProductName("대장포인트");
        // subItem.setReserved("결제 승인 건");
        // subItem.setSubAmount(10000L);
        // subItem.setSubBusinessNo("1388190354");
        // subItems.add(subItem);
        //
        // differenceRequest.setSubItems(subItems);
        // requestReqDto.setDifferenceRequests(differenceRequests);
        //
        // ObjectMapper mapper = new ObjectMapper();
        //
        // String jsonStr = mapper.writeValueAsString(requestReqDto);
        //
        // logger.info(jsonStr);
        //
        // MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        // headers.set("Content-type", "application/json");
        // headers.set("X-Naver-Client-Id", naverClientId);
        // headers.set("X-Naver-Client-Secret", naverClientSecret);
        // HttpEntity<Object> httpEntity = new HttpEntity<>(requestReqDto, headers);
        //
        // URI uri = UriComponentsBuilder.fromUriString(naverPaymentUrl).build().toUri();
        // ResponseEntity<RequestResDto> responseEntity = this.restCommTemplate.exchange(uri, HttpMethod.POST, httpEntity, RequestResDto.class);
        // logger.info(responseEntity.getBody().toString());

    }

}
