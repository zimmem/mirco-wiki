package com.zimmem.gae.wiki.controller;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.zimmem.springframework.web.exception.ResourceNotFoundException;

@ControllerAdvice
public class ErrorController {

    private static Logger log = Logger.getLogger(ErrorController.class);

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView notFound(Exception e) {
        return new ModelAndView("error");
    }
    
    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView error(Throwable e) {
        log.error(e.getMessage(), e);
        return new ModelAndView("error");
    }
}
