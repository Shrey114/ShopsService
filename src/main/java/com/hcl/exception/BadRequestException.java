package com.hcl.exception;

import org.springframework.http.HttpStatus;

import com.hcl.data.ErrorResponse;

/**
 * @author Shreyas Mehta
 *
 */
public class BadRequestException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3118433312959857490L;

	private ErrorResponse errorResponse;

	public BadRequestException() {
		this.errorResponse = defaultErrorResponse();
	}
	
	public BadRequestException(ErrorResponse errorResponse) {
		this.errorResponse = errorResponse;

		if (this.errorResponse == null) {
			this.errorResponse = defaultErrorResponse();
		}
	}

	private ErrorResponse defaultErrorResponse() {
		return new ErrorResponse(HttpStatus.BAD_REQUEST,
				"Its a bad request. Correct the URI and other request parameters");
	}

	public ErrorResponse getErrorResponse() {
		return errorResponse;
	}

	public void setErrorResponse(ErrorResponse errorResponse) {
		this.errorResponse = errorResponse;
	}

	@Override
	public String getMessage() {
		return errorResponse.getMessage();
	}

}
