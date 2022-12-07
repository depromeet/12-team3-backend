package com.depromeet.ahmatda.user.token;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;

@JsonSerialize(using = UserTokenSerializer.class)
@JsonDeserialize(using = UserTokenDeSerializer.class)
@Getter
@AllArgsConstructor
public class UserToken {

    private String value;
}
