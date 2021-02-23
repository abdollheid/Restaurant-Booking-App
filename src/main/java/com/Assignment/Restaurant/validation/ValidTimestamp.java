package com.Assignment.Restaurant.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Constraint(validatedBy = TimestampValidator.class)
@Target({ ElementType.TYPE, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ValidTimestamp {
    String message() default "Invalid timestamp, timestamp format should be 'yyyy-MM-dd HH:mm:ss' and present";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
