package com.hcl.data;

import org.springframework.http.HttpStatus;

public class ErrorResponse {

	private int code;

	private HttpStatus status;

	private String message;

	private ValidationError errors;

	public ErrorResponse() {
	}

	public ErrorResponse(HttpStatus status, String message) {
		super();
		this.code = status.value();
		this.status = status;
		this.message = message;
	}

	public ErrorResponse(HttpStatus status, String message, ValidationError errors) {
		this(status, message);
		this.errors = errors;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public ValidationError getErrors() {
		return errors;
	}

	public void setErrors(ValidationError errors) {
		this.errors = errors;
	}

}
