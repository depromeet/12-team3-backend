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

    @NotNull(message = "온보딩 카테고리를 선택해야합니다.")
    private OnBoardingCategory category;
    @NotBlank(message = "템플릿 이름이 존재해야합니다.")
    private String templateName;
    private List<String> items;
}
