package com.depromeet.ahmatda.alarm.service.impl;

import com.depromeet.ahmatda.alarm.service.FcmTokenService;
import com.depromeet.ahmatda.alarm.dto.FcmTokenRequest;
import com.depromeet.ahmatda.common.response.ErrorCode;
import com.depromeet.ahmatda.domain.fcm.FcmToken;
import com.depromeet.ahmatda.domain.fcm.adptor.FcmTokenAdaptor;
import com.depromeet.ahmatda.domain.user.User;
import com.depromeet.ahmatda.domain.user.adaptor.UserAdaptor;
import com.depromeet.ahmatda.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class FcmTokenServiceImpl implements FcmTokenService {

    private final FcmTokenAdaptor fcmTokenAdaptor;
    private final UserAdaptor userAdaptor;

    @Override
    public void renewFcmToken(final String userToken, final FcmTokenRequest fcmTokenRequest) {
        User user = userAdaptor.findByUserToken(userToken)
            .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        FcmToken fcmToken = FcmToken.builder()
            .userId(user.getId())
            .fcmToken(fcmTokenRequest.getFcmToken())
            .build();

        fcmTokenAdaptor.renewFcmToken(fcmToken);
    }
}
