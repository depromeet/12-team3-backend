package com.depromeet.ahmatda.user.dto;

import com.depromeet.ahmatda.domain.user.type.DeviceCode;
import com.depromeet.ahmatda.user.constraint.DeviceIdConstraint;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Getter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@DeviceIdConstraint
public class SignUpRequestDto {

    private DeviceCode deviceCode;

    @NotEmpty
    private String deviceId;
}
