package com.lng.dbDemo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice// Global exception handler of this application
public class ExceptionController {
	//handle the functionality when exception occured
	@ExceptionHandler(value=InvalidIdException.class)
public ResponseEntity<Object> exception(InvalidIdException e)
{
		return new ResponseEntity<>(e.getMsg(),e.getStatus());
}
	
}
