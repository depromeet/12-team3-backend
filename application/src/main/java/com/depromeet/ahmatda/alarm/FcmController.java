package com.depromeet.ahmatda.alarm;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/alarm")
public class FcmController {

    private final FcmService fcmService;

    @GetMapping
    public String pushTest(String token) throws IOException {
        log.info("token = {}", token);
        fcmService.sendMessage(token);
        return token;
    }
}
