package com.revature.errorhandling;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ApiError {
	
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy hh:mm:ss")
	private LocalDateTime timestamp;
	
	private int status;
	
	private String error;
	
	private String message;
	
	private String debugMessage;
	
	List<ApiSubError> subErrors = new ArrayList<>();
	
	public void addSubError(ApiSubError error) {
		this.subErrors.add(error);
	}	
	
	public ApiError() {
		super();
		this.timestamp = LocalDateTime.now();
	}

	public ApiError(HttpStatus status) {
		super();
		this.status = status.value();
		this.error = status.getReasonPhrase();
	}
	
	public ApiError(HttpStatus status, Throwable ex) {
		this(status); // this is constructor chaining -- we are doing everything that the above constructor is doing with this param
		this.message = "No message available";
		this.debugMessage = ex.getLocalizedMessage(); // now we don't have to keep looking at our console to figure out why things went wrong
	}
	
	public ApiError(HttpStatus status, String message, Throwable ex) {
		this(status, ex); // trigger the above constructor
		this.message = message;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDebugMessage() {
		return debugMessage;
	}

	public void setDebugMessage(String debugMessage) {
		this.debugMessage = debugMessage;
	}

	public List<ApiSubError> getSubErrors() {
		return subErrors;
	}

	public void setSubErrors(List<ApiSubError> subErrors) {
		this.subErrors = subErrors;
	}
	
}













