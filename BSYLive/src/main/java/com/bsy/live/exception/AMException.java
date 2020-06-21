/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bsy.live.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author arunsm
 */
@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class AMException extends Exception {

	public AMException(String message) {
		super(message);
	}

	public AMException(String message, Throwable throwable) {
		super(message, throwable);
	}
}
