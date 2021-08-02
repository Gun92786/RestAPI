package com.lng.dbDemo.exception;

import org.springframework.http.HttpStatus;

public class InvalidIdException extends RuntimeException {
String msg;
HttpStatus status;

public InvalidIdException(String msg,HttpStatus status)
{
	this.msg=msg;
this.status=status;	
}
public HttpStatus getStatus() {
	return status;
}
public void setStatus(HttpStatus status) {
	this.status = status;
}
public String getMsg() {
	return msg;
}

public void setMsg(String msg) {
	this.msg = msg;
}
}
