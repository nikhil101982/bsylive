package com.bsy.live.exception;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
public class ErrorResponse {
	/**
	 * Http status code
	 */
	private HttpStatus status;
	/**
	 * M23 error code
	 */
	private String code;
	/**
	 * Error description
	 */
	private String description;
	/**
	 * Error details
	 */
	private List<String> details;

	public ErrorResponse(HttpStatus status, ErrorType errorType, List<String> details) {
		super();
		this.status = status;
		this.code = errorType.getCode();
		this.description = errorType.getDescription();
		this.details = details;
	}

	public ErrorResponse(HttpStatus status, ErrorType errorType, String detail) {
		super();
		this.status = status;
		this.code = errorType.getCode();
		this.description = errorType.getDescription();
		this.details = Arrays.asList(detail);
	}
}
