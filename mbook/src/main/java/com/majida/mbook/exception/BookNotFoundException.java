package com.majida.mbook.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
//code d'erreur à retourner

@ResponseStatus(HttpStatus.NOT_FOUND)
public class BookNotFoundException extends RuntimeException{
    public BookNotFoundException(String s) {
        super(s);
    }
}
