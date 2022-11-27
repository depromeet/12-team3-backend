package com.depromeet.ahmatda.domain.user.adaptor;

import com.depromeet.ahmatda.domain.user.User;
import com.depromeet.ahmatda.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserAdaptor {

    private final UserRepository userRepository;

    public UserAdaptor(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User getByDeviceId(String deviceId) {
        return userRepository.findByDeviceId(deviceId);
    }
}
