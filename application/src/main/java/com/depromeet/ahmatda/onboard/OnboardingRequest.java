package com.depromeet.ahmatda.onboard;

import com.depromeet.ahmatda.domain.onboard.OnBoardingCategory;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class OnboardingRequest {

    @NotNull
    private OnBoardingCategory category;
    @NotBlank
    private String templateName;
    private List<String> items;
}
