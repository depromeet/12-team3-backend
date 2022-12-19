package com.depromeet.ahmatda.alarm;

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
import java.util.Arrays;

@Slf4j
@Service
@RequiredArgsConstructor
public class FcmService {

    private final ObjectMapper objectMapper;

    public void sendMessage(FcmMessage.Notification notification) throws IOException {
        final String message = makeMessage(notification);

        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(message, MediaType.get("application/json; charset=utf-8"));

        Request request = new Request.Builder()
                .url("https://fcm.googleapis.com/v1/projects/ahmatda-bb816/messages:send")
                .post(requestBody)
                .addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
                .addHeader(HttpHeaders.CONTENT_TYPE, "application/json; UTF-8")
                .build();

        Response response = okHttpClient.newCall(request).execute();
        log.info("Response = {}", response.body().string());
    }

    private String makeMessage(FcmMessage.Notification notification) throws JsonProcessingException {
        FcmMessage fcmMessage = FcmMessage.builder()
                .message(FcmMessage.Message.builder()
                        .token("dVEF-D5PQFW3HUZi0L7FCi:APA91bExYcZT2l22LrxopOc8IK2JkT1YS-pMNr0htqTQTxA_1ZjvLV_vRfniMIBKvJiy6oQyUl3AhnKvfehAuBQrJZa7QqFcdUirlFQazgZbIwpHDs5kZ3w4B3ht8xiMDyik6gULvhsP")
                        .notification(notification)
                        .build())
                .build();
        return objectMapper.writeValueAsString(fcmMessage);
    }

    private String getAccessToken() throws IOException {
        final String fcmAccountPath = "firebase/fcm-account.json";

        GoogleCredentials googleCredentials = GoogleCredentials
                .fromStream(new ClassPathResource(fcmAccountPath).getInputStream())
                .createScoped(Arrays.asList("https://www.googleapis.com/auth/cloud-platform"));
        googleCredentials.refreshIfExpired();
        return googleCredentials.getAccessToken().getTokenValue();
    }
}
