package com.depromeet.ahmatda.user.token;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

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
