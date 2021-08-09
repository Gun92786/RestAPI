package com.lng.assignment2.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.lng.assignment2.exception.InvalidCustomException;


@ControllerAdvice
public class ExceptionController {
	@ExceptionHandler(value=InvalidCustomException.class)
public ResponseEntity<String> exception(InvalidCustomException e)
{
		return new ResponseEntity<String>(e.getMsg(),e.getStatus());
}
}
