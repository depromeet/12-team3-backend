package com.depromeet.ahmatda.user.dto;

import com.depromeet.ahmatda.onboard.OnboardingRequest;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.Valid;

@Getter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {

    @Valid
    private OnboardingRequest onboardingRequest;
}
