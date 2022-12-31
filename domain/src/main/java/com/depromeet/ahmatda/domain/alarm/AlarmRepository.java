package com.depromeet.ahmatda.domain.alarm;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AlarmRepository extends JpaRepository<Alarm, Long> {

    Optional<Alarm> findByTemplateId(Long templateId);
}
