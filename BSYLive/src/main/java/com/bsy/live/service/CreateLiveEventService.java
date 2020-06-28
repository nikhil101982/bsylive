package com.bsy.live.service;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.bsy.live.model.createLiveEvent.CreateLiveEventResponse;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CreateLiveEventService {

	RestTemplate restTemplate;

	@Value("${vimeo.check.user.live.quota.url}")
	String url;

	public CreateLiveEventResponse createLiveEvent(String token, String userId) {

		HttpHeaders headers = headers(token);
		HttpEntity<String> entity = new HttpEntity<String>(headers);

		try {
			return restTemplate.exchange(url, HttpMethod.POST, entity, CreateLiveEventResponse.class).getBody();
		} catch (RestClientException e) {
			log.info("Rest client exception");
			throw new RestClientException("Rest client error" + e);
		}

	}

	private HttpHeaders headers(String token) {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(token);
		headers.set("Accept", "application/vnd.vimeo.*+json;version=3.4");
		return headers;
	}

}
