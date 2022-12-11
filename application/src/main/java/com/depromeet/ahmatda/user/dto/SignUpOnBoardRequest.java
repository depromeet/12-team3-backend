package com.depromeet.ahmatda.user.dto;

import com.depromeet.ahmatda.domain.onboard.OnBoardingCategory;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
public class SignUpOnBoardRequest {

    @NotNull
    OnBoardingCategory category;
    @NotBlank
    private String templateName;
    private List<String> items;
}
