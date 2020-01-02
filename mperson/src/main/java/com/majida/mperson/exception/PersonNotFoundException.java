package com.majida.mperson.exception;

public class PersonNotFoundException extends RuntimeException{
    public PersonNotFoundException(String s) {
        super(s);
    }
}
