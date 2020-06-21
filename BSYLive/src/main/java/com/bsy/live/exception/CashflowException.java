package com.bsy.live.exception;

/**
 * Application exception
 */
public class CashflowException extends Exception {
    
	public CashflowException(String message) {
        super(message);
    }

    public CashflowException(String message, Throwable throwable) {
        super(message, throwable);
    }
    
    
}
