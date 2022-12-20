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

    public void sendMessage(final String token) throws IOException {
        final String message = makeMessage(token);
        send(message);
    }

    private String makeMessage(final String token) throws JsonProcessingException {
        final FcmMessage.Notification notification = FcmMessage.Notification.builder()
                .title("TestTitle")
                .body("TestBody")
                .build();

        final FcmMessage fcmMessage = FcmMessage.builder()
                .message(FcmMessage.Message.builder()
                        .token(token)
                        .notification(notification)
                        .build())
                .build();
        return objectMapper.writeValueAsString(fcmMessage);
    }

    private String getAccessToken() throws IOException {
        final String fcmAccountPath = "firebase/fcm-account.json";

        final GoogleCredentials googleCredentials = GoogleCredentials
                .fromStream(new ClassPathResource(fcmAccountPath).getInputStream())
                .createScoped(Arrays.asList("https://www.googleapis.com/auth/cloud-platform"));
        googleCredentials.refreshIfExpired();
        return googleCredentials.getAccessToken().getTokenValue();
    }

    private void send(final String message) throws IOException {
        final OkHttpClient okHttpClient = new OkHttpClient();
        final RequestBody requestBody = RequestBody.create(message, MediaType.get("application/json; charset=utf-8"));

        final Request request = new Request.Builder()
                .url("https://fcm.googleapis.com/v1/projects/ahmatda-bb816/messages:send")
                .post(requestBody)
                .addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
                .addHeader(HttpHeaders.CONTENT_TYPE, "application/json; UTF-8")
                .build();

        final Response response = okHttpClient.newCall(request).execute();
        log.info("Response = {}", response.body().string());
    }
}
