package com.depromeet.ahmatda.alarm.controller.service;

import com.depromeet.ahmatda.alarm.dto.FcmTokenRequest;

public interface FcmService {

    void renewFcmToken(String userToken, FcmTokenRequest fcmTokenRequest);
}
