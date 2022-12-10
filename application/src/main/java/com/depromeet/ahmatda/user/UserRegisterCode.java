package com.depromeet.ahmatda.user;

public enum UserRegisterCode {

    REGISTERED, UNREGISTERED;

    public static UserRegisterCode getByUserFound(Boolean isExist) {
        if (isExist) return REGISTERED;
        else return UNREGISTERED;
    }
}
