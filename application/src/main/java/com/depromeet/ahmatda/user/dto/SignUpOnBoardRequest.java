package com.depromeet.ahmatda.user.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
public class SignUpOnBoardRequest {

    @NotBlank
    private String category;
    @NotBlank
    private String templateName;
    private List<String> items;
}
