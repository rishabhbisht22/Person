package com.person.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class DataNotFound {

    @ExceptionHandler(PersonNotFound.class)
    public ResponseEntity<?> personNotFoundException(PersonNotFound person, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), person.getMessage(), request.getDescription(false));
        return new ResponseEntity(errorDetails, HttpStatus.NOT_FOUND);
    }
}
