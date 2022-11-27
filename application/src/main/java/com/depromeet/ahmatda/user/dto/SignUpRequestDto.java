package com.depromeet.ahmatda.user.dto;

import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Getter
public class SignUpRequestDto {

    @NotEmpty
    private String deviceId;
}
