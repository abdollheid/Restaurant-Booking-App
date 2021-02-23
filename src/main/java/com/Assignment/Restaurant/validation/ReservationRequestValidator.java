package com.Assignment.Restaurant.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;




public class ReservationRequestValidator implements ConstraintValidator<ValidReservationRequest, String> {
    private static final DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public boolean isValid(String date, ConstraintValidatorContext constraintValidatorContext) {

        if (date == null) {
            return false;
        }

        LocalDateTime reservationTime;
        try {
            reservationTime = LocalDateTime.parse(date, formatter);
        } catch (DateTimeParseException exc) {
            return false;
        }

        if(reservationTime.isBefore(LocalDateTime.now()))
            return false ;

        return true ;
    }

}
