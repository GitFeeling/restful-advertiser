package com.gitfeeling.restadvertiser.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.gitfeeling.restadvertiser.model.ErrorResponse;
import com.gitfeeling.restadvertiser.service.AdvertiserServiceException;

@ControllerAdvice
public class ExceptionMappingAdvice {
	
	public final static int SERVER_ERROR_STATUS = 500;
	public final static String SERVER_ERROR_MESSAGE = "Unexpected server exception";
	private Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());
	
	@ExceptionHandler(AdvertiserServiceException.class)
	public ResponseEntity<ErrorResponse> handleAdvertiserServiceException(AdvertiserServiceException e) {
		logger.warn("AdvertiserServiceException", e.getStatus().description());		
		ErrorResponse errorResponse = new ErrorResponse(e.getStatus().code(), e.getStatus().description());
		return ResponseEntity
				.status(errorResponse.getStatus())
				.body(errorResponse);
	}
	
	@ExceptionHandler(Exception.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ErrorResponse handleUnExpectedException(Exception e) {
		logger.error("UnExpected server exception", e);
		ErrorResponse errorResponse = new ErrorResponse(SERVER_ERROR_STATUS, SERVER_ERROR_MESSAGE);
		return errorResponse;
	}

}
