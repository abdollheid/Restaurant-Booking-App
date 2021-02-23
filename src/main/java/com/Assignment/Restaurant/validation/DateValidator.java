package com.Assignment.Restaurant.validation;

import com.Assignment.Restaurant.utils.Date;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateValidator implements ConstraintValidator<ValidDate, String> {
    private Pattern pattern;
    private Matcher matcher;
    private static final String DATE_PATTERN = "^\\d{4}-\\d{2}-\\d{2}$";

    @Override
    public boolean isValid(String date, ConstraintValidatorContext constraintValidatorContext) {
        pattern = Pattern.compile(DATE_PATTERN);
        if (date == null) {
            return false;
        }
        matcher = pattern.matcher(date);
        return matcher.matches();
    }
}
