package com.bsy.live.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class WebException extends Exception {
	
	private static final long serialVersionUID = 1L;

	private HttpStatus httpStatus;

	public WebException(String message) {
		super(message);
	}

	public WebException(String message, Throwable throwable) {
		super(message, throwable);
	}

    public WebException(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
