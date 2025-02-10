package com.example.companysearch.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CompanySearchExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity handleException(Exception ex){
        return new ResponseEntity("Exception occurred with message: "+ ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
