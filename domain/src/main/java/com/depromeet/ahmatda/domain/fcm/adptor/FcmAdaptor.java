package com.depromeet.ahmatda.domain.fcm.adptor;

import com.depromeet.ahmatda.domain.fcm.FcmToken;
import com.depromeet.ahmatda.domain.fcm.repository.FcmRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class FcmAdaptor {

    private final FcmRepository fcmRepository;

    public void renewFcmToken(FcmToken fcmToken) {
        fcmRepository.save(fcmToken);
    }
}
