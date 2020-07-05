package com.bsy.live.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

@Component
public class CreateHttpHeaders {

	@Value("${vimeo.api.version}")
	String apiVersion;

	public HttpHeaders headers(String token) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(token);
		headers.set("Accept", "application/vnd.vimeo.*+json;version=".concat(apiVersion));
		return headers;
	}

}
