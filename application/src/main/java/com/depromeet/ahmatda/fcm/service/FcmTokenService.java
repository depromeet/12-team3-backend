package com.depromeet.ahmatda.fcm.service;

import com.depromeet.ahmatda.fcm.dto.FcmTokenRequest;

public interface FcmTokenService {

    void renewFcmToken(String userToken, FcmTokenRequest fcmTokenRequest);
}
