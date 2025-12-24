package com.mine.manager.common.customvalidation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;


public class Base64Validator implements ConstraintValidator<IsBase64, String> {
    @Override
    public void initialize(IsBase64 constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        boolean state;
        try {
            byte[] imageBytes = Base64.getDecoder().decode(value);
            BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(imageBytes));
            state = bufferedImage != null;
        } catch (IllegalArgumentException | IOException e) {
            state = false;
        }
        return state;
    }
}
