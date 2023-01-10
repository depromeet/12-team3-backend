package com.depromeet.ahmatda.domain.alarm.repository;

import com.depromeet.ahmatda.domain.alarm.AlarmMessageHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AlarmMessageHistoryRepository extends JpaRepository<AlarmMessageHistory, Long> {

    @Query("SELECT history " +
            "FROM AlarmMessageHistory history " +
            "JOIN history.alarm.template.user user " +
            "WHERE user.userToken=:userToken")
    List<AlarmMessageHistory> findAllByUserToken(@Param("userToken") String userToken);
}
