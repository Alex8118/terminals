package ru.dariedu.terminals.monitoring.api.exceptions;

import java.util.Date;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandling {

    @ExceptionHandler()
    public ResponseEntity<Object> handleException(MethodArgumentNotValidException ex) {
        ApiError apiError = ApiError.builder()
                .timestamp(new Date())
                .error("Validation error")
                .details(String.join(", ", (CharSequence) ex.getBindingResult().getAllErrors())).build();
        return new ResponseEntity<Object>(apiError, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler()
    public ResponseEntity<Object> dbException(ConstraintViolationException ex) {
        ApiError dataBaseError = ApiError.builder()
                .timestamp(new Date())
                .error("This Parameters have to be unique:")
                .details(ex.getConstraintName()).build();
        return new ResponseEntity<Object>(dataBaseError, HttpStatus.UNPROCESSABLE_ENTITY);
    }

}
