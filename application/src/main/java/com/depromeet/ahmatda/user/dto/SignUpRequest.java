package com.depromeet.ahmatda.user.dto;

import com.depromeet.ahmatda.onboard.OnboardingRequest;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {

    private OnboardingRequest onboardingRequest;
}
