package com.zerowhisper.codesearchengine.controllers;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@ControllerAdvice
@RestControllerAdvice
public class GlobalExceptionHandlerController {
    @ExceptionHandler(exception = Exception.class)
    public String exception(Exception e) {
        return e.getMessage();
    }
}
