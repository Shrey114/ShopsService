package com.hcl.exception;

import org.springframework.http.HttpStatus;

import com.hcl.data.ErrorResponse;

/**
 * @author Shreyas Mehta
 *
 */
public class ApplicationException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1900465854626041905L;

	private ErrorResponse errorResponse;

	public ApplicationException() {
		this.errorResponse = defaultErrorResponse();
	}

	public ApplicationException(ErrorResponse errorResponse) {
		this.errorResponse = errorResponse;

		if (this.errorResponse == null) {
			this.errorResponse = defaultErrorResponse();
		}
	}

	private ErrorResponse defaultErrorResponse() {
		return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
				"Something went wrong. Please contact the admininstrator.");
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
