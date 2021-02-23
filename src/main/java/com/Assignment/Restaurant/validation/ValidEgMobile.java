package com.Assignment.Restaurant.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;


@Constraint(validatedBy = EgMobileValidator.class)
@Target({ ElementType.TYPE, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ValidEgMobile {
    String message() default "Mobile number should be valid";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
