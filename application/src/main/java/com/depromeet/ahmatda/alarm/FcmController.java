package com.depromeet.ahmatda.alarm;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/alarm")
public class FcmController {

    private final FcmService fcmService;

    @GetMapping
    public void hello() throws IOException {
        FcmMessage.Notification notification = FcmMessage.Notification.builder()
                .body("body")
                .title("title")
                .build();
        fcmService.sendMessage(notification);
    }
}
