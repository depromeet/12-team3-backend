package com.depromeet.ahmatda.alarm;

import com.depromeet.ahmatda.HttpHeader;
import com.depromeet.ahmatda.common.response.ErrorCode;
import com.depromeet.ahmatda.common.response.RestResponse;
import com.depromeet.ahmatda.domain.user.User;
import com.depromeet.ahmatda.user.UserNotExistException;
import com.depromeet.ahmatda.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RestController
public class UserAlarmController {

    private final UserService userService;
    private final AlarmService alarmService;

    @PostMapping("/api/alarm")
    public RestResponse<Object> alarm(
        HttpServletRequest request,
        @RequestBody UserAlarmRequest userAlarmRequest
    ) {
        final String userToken = request.getHeader(HttpHeader.USER_TOKEN);
        final User user = userService.getUserByToken(userToken)
                .orElseThrow(() -> new UserNotExistException(ErrorCode.USER_NOT_FOUND));

        alarmService.setTemplateAlarm(user, userAlarmRequest);

        return RestResponse.ok();
    }
}

