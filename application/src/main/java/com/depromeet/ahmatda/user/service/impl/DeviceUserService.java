package com.depromeet.ahmatda.user.service.impl;

import com.depromeet.ahmatda.common.response.ErrorCode;
import com.depromeet.ahmatda.domain.user.User;
import com.depromeet.ahmatda.domain.user.adaptor.UserAdaptor;
import com.depromeet.ahmatda.exception.BusinessException;
import com.depromeet.ahmatda.onboard.OnboardingService;
import com.depromeet.ahmatda.user.UserRegisterCode;
import com.depromeet.ahmatda.user.dto.SignUpRequest;
import com.depromeet.ahmatda.user.service.UserService;
import com.depromeet.ahmatda.user.token.FcmToken;
import com.depromeet.ahmatda.user.token.UserToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeviceUserService implements UserService {

    private final UserAdaptor userAdaptor;
    private final OnboardingService onboardingService;

    @Override
    public UserRegisterCode checkUser(UserToken userToken) {
        String userTokenValue = userToken.getValue();

        return UserRegisterCode.getByUserFound(
                userAdaptor.findByUserToken(userTokenValue).isPresent()
        );
    }

    @Override
    @Transactional
    public UserToken createUser(SignUpRequest request) {
        UserToken userToken = UserToken.generate();

        User user = userAdaptor.createUser(
            User.createUserWithUserToken(userToken.getValue())
        );

        onboardingService.setUserOnboarding(user, request.getOnboardingRequest());

        return new UserToken(user.getUserToken());
    }

    @Override
    public void updateFcmToken(final String userToken, final FcmToken fcmToken) {
        final User user = userAdaptor.findByUserToken(userToken)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        user.updateFcmToken(fcmToken.getFcmToken());

        userAdaptor.updateFcmToken(user);
    }
}
