package com.depromeet.ahmatda.domain.user.repository;

import com.depromeet.ahmatda.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByDeviceId(String deviceId);
}
