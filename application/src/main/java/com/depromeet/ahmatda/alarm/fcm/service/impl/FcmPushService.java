package com.depromeet.ahmatda.alarm.fcm.service.impl;

import com.depromeet.ahmatda.alarm.fcm.FcmMessage;
import com.depromeet.ahmatda.alarm.fcm.FcmMessage.Notification;
import com.depromeet.ahmatda.common.response.ErrorCode;
import com.depromeet.ahmatda.domain.alarm.Alarm;
import com.depromeet.ahmatda.domain.fcm.adptor.FcmTokenAdaptor;
import com.depromeet.ahmatda.exception.BusinessException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FcmPushService {

    private static final String MESSAGING_SCOPE = "https://www.googleapis.com/auth/firebase.messaging";
    private static final String[] SCOPES = {MESSAGING_SCOPE};

    private final ObjectMapper objectMapper;
    private final FcmTokenAdaptor fcmTokenAdaptor;

    public void sendAlarms(List<Alarm> alarms) throws IOException {
        for (Alarm alarm : alarms) {
            String message = makeMessage(alarm);
            sendAlarm(message);
        }
    }

    private String makeMessage(final Alarm alarm) throws JsonProcessingException {
        final Long userId = alarm.getUserId();
        final String fcmToken = fcmTokenAdaptor.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND))
                .getFcmToken();

        final Notification notification = FcmMessage.Notification.createNotificationByAlarmEntity(alarm);

        final FcmMessage fcmMessage = FcmMessage.builder()
                .message(FcmMessage.Message.builder()
                        .token(fcmToken)
                        .notification(notification)
                        .build())
                .build();

        return objectMapper.writeValueAsString(fcmMessage);
    }

    private void sendAlarm(final String message) throws IOException {
        final OkHttpClient okHttpClient = new OkHttpClient();
        final RequestBody requestBody = RequestBody.create(message,
                MediaType.get("application/json; charset=utf-8"));

        final Request request = new Request.Builder()
                .url("https://fcm.googleapis.com/v1/projects/ahmatda-bb816/messages:send")
                .post(requestBody)
                .addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
                .addHeader(HttpHeaders.CONTENT_TYPE, "application/json; UTF-8")
                .build();

        final Response response = okHttpClient.newCall(request).execute();
        log.info("Response = {}", response.body().string());
    }

    private String getAccessToken() throws IOException {
        try {
            final ClassPathResource keyFileResource = new ClassPathResource("firebase/fcm-account.json");
            final InputStream keyFileInputStream = keyFileResource.getInputStream();
            final GoogleCredentials googleCredentials = GoogleCredentials
                    .fromStream(keyFileInputStream)
                    .createScoped(Arrays.asList(SCOPES));
            googleCredentials.refreshIfExpired();
            return googleCredentials.getAccessToken().getTokenValue();
        } catch (Exception e) {
            log.error(String.valueOf(e));
        }
        return null;
    }
}
