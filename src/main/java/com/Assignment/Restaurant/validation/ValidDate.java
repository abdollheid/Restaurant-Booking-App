package com.Assignment.Restaurant.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Constraint(validatedBy = DateValidator.class)
@Target({ ElementType.TYPE, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ValidDate {
    String message() default "Invalid date, Date format should be 'yyyy-MM-dd' and present";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
