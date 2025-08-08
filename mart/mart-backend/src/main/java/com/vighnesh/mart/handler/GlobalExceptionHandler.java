package com.vighnesh.mart.handler;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.vighnesh.mart.pojo.ResponseObject;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
		
	@ExceptionHandler(Exception.class)
	private ResponseObject createErrorResponse(Exception e) {
		ResponseObject  response = new ResponseObject(ResponseObject.Status.FAILURE, null, e.getMessage());
		return response;
	}
	
	@ExceptionHandler(MartException.class)
	private ResponseObject handleMartException(Exception e) {
		ResponseObject response = new ResponseObject(ResponseObject.Status.FAILURE, null, e.getMessage());
		return response;
	}
	
}

