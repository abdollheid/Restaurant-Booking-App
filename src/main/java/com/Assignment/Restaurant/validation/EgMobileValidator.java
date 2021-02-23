package com.Assignment.Restaurant.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EgMobileValidator implements ConstraintValidator<ValidEgMobile, String> {
    private Pattern pattern;
    private Matcher matcher;
    private final static String NUMBER_PATTERN = "^(\\+2)?01[0-24]{1}[0-9]{8}$";

    @Override
    public boolean isValid(String date, ConstraintValidatorContext constraintValidatorContext) {
        pattern = Pattern.compile(NUMBER_PATTERN);
        if (date == null) {
            return false;
        }
        matcher = pattern.matcher(date);
        return matcher.matches();
    }
}
