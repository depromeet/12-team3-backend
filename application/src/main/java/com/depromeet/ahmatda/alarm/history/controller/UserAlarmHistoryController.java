package com.depromeet.ahmatda.alarm.history.controller;

import com.depromeet.ahmatda.HttpHeader;
import com.depromeet.ahmatda.alarm.history.dto.AlarmMessageHistoryResponse;
import com.depromeet.ahmatda.alarm.history.service.AlarmMessageHistoryService;
import com.depromeet.ahmatda.common.response.RestResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/alarm/history")
public class UserAlarmHistoryController {

    private final AlarmMessageHistoryService alarmMessageHistoryService;

    @GetMapping
    public ResponseEntity<RestResponse<List<AlarmMessageHistoryResponse>>> getUserAlarmHistories(HttpServletRequest request) {
        String userToken = request.getHeader(HttpHeader.USER_TOKEN);
        List<AlarmMessageHistoryResponse> userAlarmHistories = alarmMessageHistoryService.getUserAlarmHistories(userToken);
        return ResponseEntity.ok().body(RestResponse.ok(userAlarmHistories));
    }
}
