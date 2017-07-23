package com.hcl.controller.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.hcl.data.ErrorResponse;
import com.hcl.exception.ApplicationException;
import com.hcl.exception.BadRequestException;
import com.hcl.exception.ObjectNotFoundException;

/**
 * @author Shreyas Mehta
 *
 */
@ControllerAdvice
public class GlobalControllerExceptionHandler {

	@ExceptionHandler(value = { BadRequestException.class })
	protected ResponseEntity<ErrorResponse> handleBadRequest(BadRequestException ex) {
		return ResponseEntity.badRequest().body(ex.getErrorResponse());
	}

	@ExceptionHandler(value = { ObjectNotFoundException.class })
	protected ResponseEntity<ErrorResponse> handleNotFound(ObjectNotFoundException ex) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getErrorResponse());
	}

	@ExceptionHandler(value = { ApplicationException.class })
	protected ResponseEntity<ErrorResponse> handleIntegrations(ApplicationException ex) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getErrorResponse());
	}
}
