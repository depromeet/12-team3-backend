package com.depromeet.ahmatda.user.token;

import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
@ToString
public class FcmToken {

    @NotBlank(message = "FCM 토큰은 필수입니다.")
    private String fcmToken;
}
