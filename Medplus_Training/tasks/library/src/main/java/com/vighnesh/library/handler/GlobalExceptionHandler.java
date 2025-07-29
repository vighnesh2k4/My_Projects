package com.vighnesh.library.handler;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND) 
    public ModelAndView handleResourceNotFound(ResourceNotFoundException ex) {
        ModelAndView mav = new ModelAndView("error");
        mav.addObject("errorMessage", ex.getMessage());
        mav.addObject("errorCode", HttpStatus.NOT_FOUND.value());
        return mav;
    }

    @ExceptionHandler(InvalidInputException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ModelAndView handleInvalidInput(InvalidInputException ex) {
        ModelAndView mav = new ModelAndView("error");
        mav.addObject("errorMessage", ex.getMessage());
        mav.addObject("errorCode", HttpStatus.BAD_REQUEST.value());
        return mav;
    }

    @ExceptionHandler(LogicalError.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ModelAndView handleLogicalError(LogicalError ex) {
        ModelAndView mav = new ModelAndView("error");
        mav.addObject("errorMessage", ex.getMessage());
        mav.addObject("errorCode", HttpStatus.CONFLICT.value());
        return mav;
    }

    @ExceptionHandler({DatabaseException.class, DataAccessException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) 
    public ModelAndView handleDatabaseException(Exception ex) {
        ModelAndView mav = new ModelAndView("error");
        String message = ex.getMessage();
        mav.addObject("errorMessage", message);
        mav.addObject("errorCode", HttpStatus.INTERNAL_SERVER_ERROR.value());
        return mav;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView handleAllExceptions(Exception ex) {
        ModelAndView mav = new ModelAndView("error");
        mav.addObject("errorMessage", "An unexpected error occurred: " + ex.getMessage());
        mav.addObject("errorCode", HttpStatus.INTERNAL_SERVER_ERROR.value());
        ex.printStackTrace(); 
        return mav;
    }
}