package com.hcl.exception;

import org.springframework.http.HttpStatus;

import com.hcl.data.ErrorResponse;

/**
 * @author Shreyas Mehta
 *
 */
public class ObjectNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 145792859248966448L;

	private ErrorResponse errorResponse;

	public ObjectNotFoundException() {
		this.errorResponse = defaultErrorResponse();
	}
	
	public ObjectNotFoundException(ErrorResponse errorResponse) {
		this.errorResponse = errorResponse;

		if (this.errorResponse == null) {
			this.errorResponse = defaultErrorResponse();
		}
	}

	private ErrorResponse defaultErrorResponse() {
		return new ErrorResponse(HttpStatus.NOT_FOUND, "Requested object doesnot exist.");
	}

	public ObjectNotFoundException(String message) {
		super(message);
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
