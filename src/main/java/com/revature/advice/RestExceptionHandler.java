package com.revature.advice;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.revature.errorhandling.ApiError;
import com.revature.errorhandling.ApiValidationError;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
	
	private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
		return ResponseEntity.status(apiError.getStatus()).body(apiError);
	}
	/*
	 * Intercept exceptions caused by JHibernate Validation
	 * 
	 * What happens if a User uses a POST request to send and INVALID User object
	 */
	
	// This is designed to capture any exception that might occur when a controller method takes in a "bad" object
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		String error = "Request failed validation";
		
		// instantiate an ApiError object
		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, error, ex);
		
		// Next we can find out exactly What went wrong
		for(FieldError e : ex.getFieldErrors()) {
			apiError.addSubError(new ApiValidationError(e.getObjectName(), e.getDefaultMessage(), e.getField(), e.getRejectedValue()));
		}
		
		
		return buildResponseEntity(apiError);
	}
	
	/*
	 * Intercepts exceptions that are caused by Invalid JSON
	 * 
	 */
	
	protected ResponseEntity<Object> handleHttpMessageNotReadable(
			MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		String error = "Malformed JSON request";
		
		return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, error, ex));
		
	}
}













