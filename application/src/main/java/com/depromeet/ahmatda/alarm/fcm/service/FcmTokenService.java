package com.depromeet.ahmatda.alarm.fcm.service;

import com.depromeet.ahmatda.alarm.fcm.dto.FcmTokenRequest;

public interface FcmTokenService {

    void renewFcmToken(String userToken, FcmTokenRequest fcmTokenRequest);
}
