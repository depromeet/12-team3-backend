package com.depromeet.ahmatda.user.service;

import com.depromeet.ahmatda.domain.user.User;
import com.depromeet.ahmatda.user.UserRegisterCode;
import com.depromeet.ahmatda.user.token.UserToken;

public interface UserService {

    UserRegisterCode checkUser(UserToken userToken);
    User createUser();
}
