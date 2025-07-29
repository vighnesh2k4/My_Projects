package com.kbc.KbcApp.handler;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.kbc.KbcApp.pojos.ResponseObject;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
	
	@Autowired
	private HttpSession session;
	
	@ExceptionHandler(Exception.class)
	private ResponseObject createErrorResponse(Exception e) {
		ResponseObject  response = new ResponseObject();
		response.setStatus(ResponseObject.Status.FAILURE);
		response.setMessage(e.getMessage());
		return response;
	}
	
	@ExceptionHandler(KbcException.class)
	private ResponseObject handleKbcException(Exception e) {
		ResponseObject response = new ResponseObject();
		response.setStatus(ResponseObject.Status.FAILURE);
		response.setMessage(e.getMessage());
		log.error(session.getAttribute("userName")+"got an "+" Error : {}", e.getMessage());
		return response;
	}

//	@ExceptionHandler(KbcException.class)
//	private ResponseEntity<Map<String,Object>> handleKbcException(Exception e) {
//		Map<String, Object> response = new HashMap<>();
//		response.put("status", FAILURE);
//		response.put("message", e.getMessage());
//		log.error(session.getAttribute("userName")+"got an "+" Error : {}", e.getMessage());
//		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
//	}

}
