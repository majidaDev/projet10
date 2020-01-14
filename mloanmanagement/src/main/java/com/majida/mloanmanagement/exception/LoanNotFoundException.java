package com.majida.mloanmanagement.exception;

public class LoanNotFoundException extends RuntimeException{
    public LoanNotFoundException(String s) {
        super(s);
    }
}
