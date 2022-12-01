package com.depromeet.ahmatda.domain.user.type;

public enum DeviceCode {
    IOS("^[A-Z0-9]{8,8}-[A-Z0-9]{4,4}-[A-Z0-9]{4,4}-[A-Z0-9]{4,4}-[A-Z0-9]{12,12}$"),
    ANDROID("[a-z0-9]{16,16}");

    private final String pattern;

    DeviceCode(String pattern) {
        this.pattern = pattern;
    }

    public Boolean checkRegex(String deviceId) {
        return deviceId.matches(this.pattern);
    }
}
