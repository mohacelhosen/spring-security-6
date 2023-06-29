package com.mohacel.springsecurity.exception;

public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException(){}
    public InvalidCredentialsException(String message){super(message);}
}
