package com.depromeet.ahmatda.user.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Getter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequestDto {

    @NotEmpty
    private String deviceId;
}
