package com.mohacel.springsecurity.exception;

import javax.security.sasl.AuthenticationException;

public class BadCredentialsException extends AuthenticationException {
    public BadCredentialsException(){}
    public BadCredentialsException(String message){super(message);}
}
