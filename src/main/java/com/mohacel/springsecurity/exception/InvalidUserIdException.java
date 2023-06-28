package com.mohacel.springsecurity.exception;

public class InvalidUserIdException extends RuntimeException{
    public InvalidUserIdException(){}
    public InvalidUserIdException(String message){super(message);}
}
