package com.depromeet.ahmatda.user.token;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FcmToken {

    @NotBlank(message = "FCM TOKEN은 필수값입니다.")
    private String fcmToken;

}
