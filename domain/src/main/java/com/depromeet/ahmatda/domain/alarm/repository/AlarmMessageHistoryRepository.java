package com.depromeet.ahmatda.domain.alarm.repository;

import com.depromeet.ahmatda.domain.alarm.AlarmMessageHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlarmMessageHistoryRepository extends JpaRepository<AlarmMessageHistory, Long> {
}
