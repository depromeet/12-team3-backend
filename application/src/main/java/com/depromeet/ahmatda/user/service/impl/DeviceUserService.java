package com.depromeet.ahmatda.user.service.impl;

import com.depromeet.ahmatda.common.response.ErrorCode;
import com.depromeet.ahmatda.domain.user.User;
import com.depromeet.ahmatda.domain.user.adaptor.UserAdaptor;
import com.depromeet.ahmatda.user.UserExistException;
import com.depromeet.ahmatda.user.dto.SignUpRequestDto;
import com.depromeet.ahmatda.user.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class DeviceUserService implements UserService {

    private final UserAdaptor userAdaptor;

    public DeviceUserService(UserAdaptor userAdaptor) {
        this.userAdaptor = userAdaptor;
    }

    @Override
    public User createUser(SignUpRequestDto request) {
        String deviceId = request.getDeviceId();
        checkExistUser(deviceId);

        User user = User.createUserWithDeviceId(request.getDeviceId());
        return userAdaptor.createUser(user);
    }

    private void checkExistUser(String deviceId) {
        userAdaptor.getByDeviceId(deviceId).ifPresent(t -> {
            throw new UserExistException(ErrorCode.EXIST_USER);
        });
    }

    @Override
    public User getByDeviceId(String deviceId) {
        return null;
    }
}
