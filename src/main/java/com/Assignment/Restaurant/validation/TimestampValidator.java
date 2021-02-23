package com.Assignment.Restaurant.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class TimestampValidator implements ConstraintValidator<ValidTimestamp, String> {
    private Pattern pattern;
    private Matcher matcher;
    private static final String TIMESTAMP_PATTERN = "^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}$";

    @Override
    public boolean isValid(String date , ConstraintValidatorContext constraintValidatorContext) {
        pattern = Pattern.compile(TIMESTAMP_PATTERN);
        if (date == null) {
            return false;
        }
        matcher = pattern.matcher(date);
        return matcher.matches();
    }
}
