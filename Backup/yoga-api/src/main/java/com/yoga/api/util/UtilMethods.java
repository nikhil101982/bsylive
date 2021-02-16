package com.yoga.api.util;

import com.yoga.api.constant.ApiConstants;
import com.yoga.api.model.StatusMessageResponse;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UtilMethods {
	
	private StatusMessageResponse statusResponse = new StatusMessageResponse();
	
	public StatusMessageResponse errorResponse(String message) {
		statusResponse.setMessage(message);
		statusResponse.setStatus(ApiConstants.FAILURE);
		return statusResponse;
	}
	
	
	public StatusMessageResponse successResponse(String message) {
		statusResponse.setMessage(message);
		statusResponse.setStatus(ApiConstants.SUCCESS);
		
		return statusResponse;
	}

	public void errorResponse1(String message) {
		statusResponse.setMessage(message);
		statusResponse.setStatus(ApiConstants.FAILURE);
	}
	
	
	public void successResponse1(String message) {
		statusResponse.setMessage(message);
		statusResponse.setStatus(ApiConstants.SUCCESS);
		
	}

}
