package com.Assignment.Restaurant.exception;

import com.Assignment.Restaurant.RestaurantApplication;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
    private static final Logger logger = LogManager.getLogger(RestaurantApplication.class);


    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        logger.trace("@App-CustomExceptionHandler-handleMissingServletRequestParameter");

        ApiError apiError =
                new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), new ErrorField(ex.getParameterName(), "parameter is missing"));

        return new ResponseEntity<>(
                apiError, new HttpHeaders(), apiError.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        logger.trace("@App-CustomExceptionHandler-handleMethodArgumentNotValid");

        List<ErrorField> errors = new ArrayList<>();

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            ErrorField errorField = new ErrorField(error.getField(), error.getDefaultMessage());
            errors.add(errorField);
        }
        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            ErrorField errorField = new ErrorField(error.getObjectName(), error.getDefaultMessage());
            errors.add(errorField);

        }

        ApiError apiError =
                new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);
        return handleExceptionInternal(
                ex, apiError, headers, apiError.getStatus(), request);


    }

    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        logger.trace("@App-CustomExceptionHandler-handleBindException");

        List<ErrorField> errors = new ArrayList<>();

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            ErrorField errorField = new ErrorField(error.getField(), error.getDefaultMessage());
            errors.add(errorField);
        }

        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            ErrorField errorField = new ErrorField(error.getObjectName(), error.getDefaultMessage());
            errors.add(errorField);

        }

        ApiError apiError =
                new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);
        return handleExceptionInternal(
                ex, apiError, headers, apiError.getStatus(), request);

    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex, WebRequest request) {
        logger.trace("@App-CustomExceptionHandler-handleConstraintViolation");

        List<ErrorField> errors = new ArrayList<>();

        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            errors.add(new ErrorField(violation.getPropertyPath().toString(), violation.getMessage()));  //                    violation.getRootBeanClass().getName() + " " +    display the class causing the error
        }

        ApiError apiError =
                new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);

        return new ResponseEntity<>(
                apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex, WebRequest request) {
        logger.trace("@App-CustomExceptionHandler-handleMethodArgumentTypeMismatch");

        ApiError apiError =
                new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), new ErrorField(ex.getName(), " should be of type " + ex.getRequiredType().getName()));

        return new ResponseEntity<>(
                apiError, new HttpHeaders(), apiError.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        logger.trace("@App-CustomExceptionHandler-handleNoHandlerFoundException");

        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, ex.getLocalizedMessage(), new ErrorField("error", "No handler found for " + ex.getHttpMethod() + " " + ex.getRequestURL()));

        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler({DataIntegrityViolationException.class})
    public ResponseEntity<Object> handleConflict(HttpServletRequest req, DataIntegrityViolationException exc) {
        logger.trace("@App-CustomExceptionHandler-handleConflict");
        String rootCause = getToString(exc.getRootCause());

        logger.debug("@App-CustomExceptionHandler-handleConflict: rootCause:" + rootCause);

        String error = "Error", message = rootCause;
        if (rootCause != null) {
            if (rootCause.contains("Duplicate entry")) {
                message = "Is used";
                if (rootCause.contains("email")) { // all unique colms we have are 'email', 'mobile'
                    error = "email";
                } else {
                    if (rootCause.contains("mobile")) {
                        error = "mobile";
                    }
                }
            } else {
                logger.info("@App-CustomExceptionHandler-handleConflict: Unknown conflict: " + rootCause);
            }
        } else {
            logger.info("@App-CustomExceptionHandler-handleConflict: null root cause: " + exc.getMessage());
        }

        ApiError apiError =
                new ApiError(HttpStatus.BAD_REQUEST, exc.getMessage(), new ErrorField(error, message));

        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());


    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        logger.trace("@App-CustomExceptionHandler-handleHttpRequestMethodNotSupported");


        StringBuilder builder = new StringBuilder();
        builder.append(ex.getMethod());
        builder.append(
                " method is not supported for this request. Supported methods are ");
        ex.getSupportedHttpMethods().forEach(t -> builder.append(t + " "));

        ApiError apiError = new ApiError(HttpStatus.METHOD_NOT_ALLOWED,
                ex.getLocalizedMessage(), new ErrorField("Unsupported Method", builder.toString()));

        return new ResponseEntity<>(
                apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleAll(Exception ex, WebRequest request) {
        logger.info("@App-CustomExceptionHandler-handleAll");

        ApiError apiError = new ApiError(
                HttpStatus.INTERNAL_SERVER_ERROR, ex.getLocalizedMessage(), new ErrorField("Error", "error occurred"));

        return new ResponseEntity<>(
                apiError, new HttpHeaders(), apiError.getStatus());
    }

    private String getToString(Object object) {
        if (object == null) return null;
        return object.toString();
    }


}
