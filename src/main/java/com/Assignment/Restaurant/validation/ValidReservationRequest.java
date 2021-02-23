package com.Assignment.Restaurant.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Constraint(validatedBy = ReservationRequestValidator.class)
@Target({ ElementType.TYPE, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ValidReservationRequest {
    String message() default "Invalid reservation timestamp, timestamp should be set, format should be 'yyyy-MM-dd HH:mm:ss', shouldnt be expired";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};


}
