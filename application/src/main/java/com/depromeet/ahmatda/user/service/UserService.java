package com.depromeet.ahmatda.user.service;

import com.depromeet.ahmatda.domain.user.User;
import com.depromeet.ahmatda.user.dto.SignUpRequestDto;

public interface UserService {

    User createUser(SignUpRequestDto request);
    User getByDeviceId(String deviceId);
}
