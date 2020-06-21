package com.bsy.live.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Type of error
 */
@Getter
@AllArgsConstructor
public enum ErrorType {
	
	
	UNKNOWN("000", "Internal Error occurred"), 
	INFORMATION("100", "Infornation"), 
	SUCCESSFUL("200","Success"), 
	REDIRECTS("300","Redirects Exception"), 
	CLIENT("400", "Client Exception"), 
	SERVER("500", "Server Exception");

	private String code;
	private String description;
	

}
