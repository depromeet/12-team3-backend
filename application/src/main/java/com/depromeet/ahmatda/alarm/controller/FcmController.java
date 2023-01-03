package com.depromeet.ahmatda.alarm.controller;

import com.depromeet.ahmatda.HttpHeader;
import com.depromeet.ahmatda.alarm.FcmPushService;
import com.depromeet.ahmatda.alarm.service.FcmService;
import com.depromeet.ahmatda.common.response.RestResponse;
import com.depromeet.ahmatda.alarm.dto.FcmTokenRequest;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping()
public class FcmController {

    private final FcmPushService fcmPushService;
    private final FcmService fcmService;

    @GetMapping("/api/alarm/fcm-test")
    public String pushTest(String token) throws IOException {
        log.info("token = {}", token);
        fcmPushService.sendMessage(token);
        return token;
    }

    @PostMapping("/api/user/token")
    public ResponseEntity<RestResponse<Object>> renewFcmToken(@Valid @RequestBody FcmTokenRequest fcmTokenRequest, HttpServletRequest request) {
        final String userToken = request.getHeader(HttpHeader.USER_TOKEN);
        fcmService.renewFcmToken(userToken, fcmTokenRequest);
        return ResponseEntity.ok().body(RestResponse.ok());
    }
}
