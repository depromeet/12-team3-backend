package com.depromeet.ahmatda.alarm.fcm.controller;

import com.depromeet.ahmatda.HttpHeader;
import com.depromeet.ahmatda.alarm.fcm.service.FcmTokenService;
import com.depromeet.ahmatda.common.response.RestResponse;
import com.depromeet.ahmatda.alarm.fcm.dto.FcmTokenRequest;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping()
public class FcmTokenController {

    private final FcmTokenService fcmTokenService;

    @PostMapping("/api/user/token")
    public ResponseEntity<RestResponse<Object>> renewFcmToken(@Valid @RequestBody FcmTokenRequest fcmTokenRequest, HttpServletRequest request) {
        final String userToken = request.getHeader(HttpHeader.USER_TOKEN);
        fcmTokenService.renewFcmToken(userToken, fcmTokenRequest);
        return ResponseEntity.ok().body(RestResponse.ok());
    }
}
