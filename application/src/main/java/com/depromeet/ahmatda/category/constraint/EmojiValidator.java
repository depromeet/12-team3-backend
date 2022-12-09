package com.depromeet.ahmatda.category.constraint;

import com.depromeet.ahmatda.domain.category.Emoji;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmojiValidator implements ConstraintValidator<EmojiConstraint, Emoji> {

    @Override
    public void initialize(EmojiConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Emoji value, ConstraintValidatorContext context) {
        if (value.equals(Emoji.EXCEPTION)) {
            return false;
        }

        return true;
    }
}
