package com.depromeet.ahmatda.domain.user.repository;

import com.depromeet.ahmatda.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByDeviceId(String deviceId);
}
