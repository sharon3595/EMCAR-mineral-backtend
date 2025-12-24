package com.mine.manager.common.customvalidation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = Base64Validator.class)
public @interface IsBase64 {
    String message() default "El logo no es un valor Base64 vÃ¡lido";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
