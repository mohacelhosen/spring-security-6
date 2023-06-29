package com.mohacel.springsecurity.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidUserIdException.class)
    public ResponseEntity<ExceptionInfo> invalidUserIdException(InvalidUserIdException invalidUserId){
        ExceptionInfo info = new ExceptionInfo();
        info.setCode("InvalidID:101");
        info.setMessage(invalidUserId.getMessage());
        return new ResponseEntity<>(info, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public  ResponseEntity<ExceptionInfo> invalidCredentials(InvalidCredentialsException invalidCredentials){
        ExceptionInfo info = new ExceptionInfo();
        info.setCode("Invalid:144");
        info.setMessage(invalidCredentials.getMessage());
        return new ResponseEntity<>(info, HttpStatus.NOT_FOUND);
    }
}
