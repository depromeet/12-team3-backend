package com.depromeet.ahmatda.user.service.impl;

import com.depromeet.ahmatda.domain.user.User;
import com.depromeet.ahmatda.domain.user.adaptor.UserAdaptor;
import com.depromeet.ahmatda.onboard.OnboardingService;
import com.depromeet.ahmatda.user.UserRegisterCode;
import com.depromeet.ahmatda.user.dto.SignUpOnBoardRequest;
import com.depromeet.ahmatda.user.service.UserService;
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
    public UserToken createUser(SignUpOnBoardRequest request) {
        UserToken userToken = UserToken.generate();

        User user = userAdaptor.createUser(
            User.createUserWithUserToken(userToken.getValue())
        );

        onboardingService.setUserOnboarding(user, request.getOnboardingRequest());

        return new UserToken(user.getUserToken());
    }
}
