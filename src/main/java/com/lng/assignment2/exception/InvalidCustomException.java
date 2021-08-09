package com.lng.assignment2.exception;

import org.springframework.http.HttpStatus;

public class InvalidCustomException extends Throwable{
	
	String msg;
	HttpStatus status;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public InvalidCustomException(String msg,HttpStatus status)
	{
		this.msg=msg;
	this.status=status;	
	}
}
