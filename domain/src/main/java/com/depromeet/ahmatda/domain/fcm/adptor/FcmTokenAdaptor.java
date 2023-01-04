package com.depromeet.ahmatda.domain.fcm.adptor;

import com.depromeet.ahmatda.domain.fcm.FcmToken;
import com.depromeet.ahmatda.domain.fcm.repository.FcmRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class FcmTokenAdaptor {

    private final FcmRepository fcmRepository;

    public void renewFcmToken(FcmToken fcmToken) {
        fcmRepository.save(fcmToken);
    }

    public Optional<FcmToken> findById(Long userId) {
        return fcmRepository.findById(userId);
    }
}
