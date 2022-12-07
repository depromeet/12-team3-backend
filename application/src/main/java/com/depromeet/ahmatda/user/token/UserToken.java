package com.depromeet.ahmatda.user.token;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@JsonSerialize(using = UserTokenSerializer.class)
@JsonDeserialize(using = UserTokenDeSerializer.class)
@Getter
@AllArgsConstructor
public class UserToken {

    private String value;

    public static UserToken generate() {
        String userTokenStr = UUID.randomUUID()
                .toString()
                .replace("-", "")
                .substring(0, 20);

        return new UserToken(userTokenStr);
    }
}
