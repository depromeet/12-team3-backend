package com.depromeet.ahmatda.alarm.service;

import com.depromeet.ahmatda.alarm.dto.FcmTokenRequest;

public interface FcmTokenService {

    void renewFcmToken(String userToken, FcmTokenRequest fcmTokenRequest);
}
