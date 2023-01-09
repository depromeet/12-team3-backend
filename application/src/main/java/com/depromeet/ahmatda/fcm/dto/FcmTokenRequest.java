package com.depromeet.ahmatda.fcm.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FcmTokenRequest {

    @NotBlank(message = "FCM TOKEN은 필수값입니다.")
    private String fcmToken;

}
