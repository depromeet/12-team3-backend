package com.depromeet.ahmatda.user.service;

import com.depromeet.ahmatda.user.UserRegisterCode;
import com.depromeet.ahmatda.user.dto.SignUpRequest;
import com.depromeet.ahmatda.user.token.FcmToken;
import com.depromeet.ahmatda.user.token.UserToken;

public interface UserService {

    UserRegisterCode checkUser(UserToken userToken);
    UserToken createUser(SignUpRequest request);
    void renewFcmToken(final String userToken, final FcmToken token);
}
