package com.zph.springbootadvanced.exception;

import com.zph.springbootadvanced.domain.Book;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class BookNotFoundException extends RuntimeException {

    public BookNotFoundException(){};
    public BookNotFoundException(String message){};
    public BookNotFoundException(String message,Throwable cause){
        super(message,cause);
    }
}
