package com.depromeet.ahmatda.user.service;

import com.depromeet.ahmatda.user.UserRegisterCode;
import com.depromeet.ahmatda.user.dto.SignUpOnBoardRequest;
import com.depromeet.ahmatda.user.token.UserToken;

public interface UserService {

    UserRegisterCode checkUser(UserToken userToken);
    UserToken createUser(SignUpOnBoardRequest request);
}
