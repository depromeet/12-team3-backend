package com.depromeet.ahmatda.user.service.impl;

import com.depromeet.ahmatda.domain.user.adaptor.UserAdaptor;
import com.depromeet.ahmatda.user.UserRegisterCode;
import com.depromeet.ahmatda.user.service.UserService;
import com.depromeet.ahmatda.user.token.UserToken;
import org.springframework.stereotype.Service;

@Service
public class DeviceUserService implements UserService {

    private final UserAdaptor userAdaptor;

    public DeviceUserService(UserAdaptor userAdaptor) {
        this.userAdaptor = userAdaptor;
    }

    @Override
    public UserRegisterCode checkUser(UserToken userToken) {
        String userTokenValue = userToken.getValue();

        return UserRegisterCode.getByUserFound(
                userAdaptor.findByUserToken(userTokenValue).isPresent()
        );
    }
}
