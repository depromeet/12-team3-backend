package com.depromeet.ahmatda.domain.fcm.repository;

import com.depromeet.ahmatda.domain.fcm.FcmToken;
import com.depromeet.ahmatda.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FcmRepository extends JpaRepository<FcmToken, User> {

}
