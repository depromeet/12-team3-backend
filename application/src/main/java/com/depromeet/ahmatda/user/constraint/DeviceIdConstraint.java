package com.depromeet.ahmatda.user.constraint;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.CONSTRUCTOR})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DeviceIdValidator.class)
public @interface DeviceIdConstraint {
    String message() default "deviceCode-deviceId 값이 유효하지 않습니다";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
