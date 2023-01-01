package com.depromeet.ahmatda.user.service;

import com.depromeet.ahmatda.domain.user.User;
import com.depromeet.ahmatda.user.UserRegisterCode;
import com.depromeet.ahmatda.user.dto.SignUpRequest;
import com.depromeet.ahmatda.user.token.UserToken;
import java.util.Optional;

public interface UserService {

    UserRegisterCode checkUser(UserToken userToken);
    UserToken createUser(SignUpRequest request);
    Optional<User> getUserByToken(String userToken);
}
