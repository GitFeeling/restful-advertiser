package com.gitfeeling.restadvertiser.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.gitfeeling.restadvertiser.exception.AdvertiserServiceException;
import com.gitfeeling.restadvertiser.model.ErrorResponse;

@ControllerAdvice
public class ExceptionMappingAdvice {
	
	public final static int SERVER_ERROR_STATUS = 500;
	public final static String SERVER_ERROR_MESSAGE = "Unexpected server exception";
	
	@ExceptionHandler(AdvertiserServiceException.class)
	public ResponseEntity<ErrorResponse> handleAdvertiserServiceException(AdvertiserServiceException e) {
		ErrorResponse errorResponse = new ErrorResponse(e.getStatus(), e.getMessage());
		return ResponseEntity
				.status(errorResponse.getStatus())
				.body(errorResponse);
	}
	
	@ExceptionHandler(Exception.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ErrorResponse handleUnExpectedException(Exception e) {
		ErrorResponse errorResponse = new ErrorResponse(SERVER_ERROR_STATUS, SERVER_ERROR_MESSAGE);
		return errorResponse;
	}

}
