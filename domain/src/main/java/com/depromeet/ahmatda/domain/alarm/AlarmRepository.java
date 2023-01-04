package com.depromeet.ahmatda.domain.alarm;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import org.springframework.data.jpa.repository.Query;

public interface AlarmRepository extends JpaRepository<Alarm, Long> {

    Optional<Alarm> findByTemplateId(Long templateId);

    @Query("SELECT alarm FROM Alarm alarm WHERE alarm.isSend = false AND alarm.isActivated = true")
    List<Alarm> findAllUnsentAlarm();
}
