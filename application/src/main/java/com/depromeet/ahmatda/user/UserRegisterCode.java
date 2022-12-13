package com.depromeet.ahmatda.user;

import com.depromeet.ahmatda.common.utils.EnumType;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum UserRegisterCode implements EnumType {

    REGISTERED("등록된 유저"), UNREGISTERED("미등록 유저");

    private final String label;

    public static UserRegisterCode getByUserFound(Boolean isExist) {
        if (isExist) return REGISTERED;
        else return UNREGISTERED;
    }

    @Override
    public String getName() {
        return this.name();
    }

    @Override
    public String getLabel() {
        return this.label;
    }
}
