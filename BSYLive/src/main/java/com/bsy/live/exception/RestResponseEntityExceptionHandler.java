package com.bsy.live.exception;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.RestClientException;

import lombok.extern.slf4j.Slf4j;

/**
 * Base Exception Handler
 */

@Slf4j
@ControllerAdvice
public class RestResponseEntityExceptionHandler {

	@ExceptionHandler({ MethodArgumentNotValidException.class })
	public ResponseEntity<ErrorResponse> validationException(MethodArgumentNotValidException ex) {
		log.error(ex.getMessage(), ex);

		List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
		List<String> errors = new ArrayList<>();
		for (FieldError fieldError : fieldErrors) {
			errors.add(fieldError.getDefaultMessage());
		}

		ErrorResponse response = new ErrorResponse();

		response.setCode(HttpStatus.METHOD_NOT_ALLOWED.toString());
		response.setDescription(ErrorType.CLIENT.toString());
		response.setDetails(errors);

		return new ResponseEntity<>(response, response.getStatus());
	}

	@ExceptionHandler({ RestClientException.class })
	public ResponseEntity<ErrorResponse> restClientException(ClientHttpResponse httpResponse) throws IOException {

		ErrorResponse res = new ErrorResponse();
		

		if (httpResponse.getStatusCode().series() == HttpStatus.Series.SERVER_ERROR) {

			res.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			res.setCode("5xx");
			res.setDescription("Server error");

		} else if (httpResponse.getStatusCode().series() == HttpStatus.Series.CLIENT_ERROR) {

			if (httpResponse.getStatusCode() == HttpStatus.NOT_FOUND) {

				res.setStatus(HttpStatus.NOT_FOUND);
				res.setCode("4xx");
				res.setDescription("Client error");
			}

		}else {
			res.setStatus(HttpStatus.NOT_FOUND);
			res.setCode(ErrorType.UNKNOWN.toString());
			res.setDescription("Unknown HttpStatus Exception");

		}

		return new ResponseEntity<>(res, res.getStatus());

	}
}
