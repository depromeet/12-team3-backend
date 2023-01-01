package com.depromeet.ahmatda.user.service;

import com.depromeet.ahmatda.domain.user.User;
import com.depromeet.ahmatda.user.UserRegisterCode;
import com.depromeet.ahmatda.user.dto.SignUpRequest;
import com.depromeet.ahmatda.alarm.dto.FcmTokenRequest;
import com.depromeet.ahmatda.user.token.UserToken;

import java.util.Optional;

public interface UserService {

    UserRegisterCode checkUser(UserToken userToken);
    UserToken createUser(SignUpRequest request);
    void renewFcmToken(final String userToken, final FcmTokenRequest token);
    Optional<User> getUserByToken(String userToken);
}
