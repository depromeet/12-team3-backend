package com.depromeet.ahmatda.repository;

import com.depromeet.ahmatda.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByDeviceId(String deviceId);
}
