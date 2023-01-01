package com.depromeet.ahmatda.domain.fcm.repository;

import com.depromeet.ahmatda.domain.fcm.FcmToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FcmRepository extends JpaRepository<FcmToken, Long> {

}
