package com.depromeet.ahmatda.user.constraint;

import com.depromeet.ahmatda.user.dto.SignUpRequestDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DeviceIdValidator implements ConstraintValidator<DeviceIdConstraint, SignUpRequestDto> {

    private String message;

    @Override
    public void initialize(DeviceIdConstraint constraintAnnotation) {
        message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(SignUpRequestDto value, ConstraintValidatorContext context) {
        if (value.getDeviceCode() == null) {
            context.buildConstraintViolationWithTemplate("null 값은 허용되지 않습니다.")
                    .addPropertyNode( "deviceCode" )
                    .addConstraintViolation();
            return false;
        }

        if (value.getDeviceCode().checkRegex(value.getDeviceId())) return true;

        context.buildConstraintViolationWithTemplate(message)
                .addPropertyNode( "deviceCode" )
                .addConstraintViolation();
        return false;
    }
}
