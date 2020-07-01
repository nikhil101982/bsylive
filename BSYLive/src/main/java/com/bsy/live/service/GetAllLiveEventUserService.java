package com.bsy.live.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.bsy.live.model.createLiveEvent.LiveEventResponse;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class GetAllLiveEventUserService {

	RestTemplate restTemplate;

	@Value("${vimeo.live.event.create.url}")
	String getLiveEventsUrl;

	@Value("${vimeo.live.events.url}")
	String liveEventsUrl;

	@Value("${vimeo.live.events.me.url}")
	String meLiveEventUrl;

	public LiveEventResponse createLiveEvent(String userId) {

//		HttpHeaders headers = headers(token);
//		HttpEntity<String> entity = new HttpEntity<String>(headers);

		try {
			return restTemplate.exchange(getLiveEventsUrl, HttpMethod.GET, null, LiveEventResponse.class)
					.getBody();
		} catch (RestClientException e) {
			log.info("Rest client exception");
			throw new RestClientException("Rest client error" + e);
		}

	}

	public LiveEventResponse liveEvents() {

//		HttpHeaders headers = headers(token);
//		HttpEntity<String> entity = new HttpEntity<String>(headers);

		try {
			return restTemplate.exchange(liveEventsUrl, HttpMethod.GET, null, LiveEventResponse.class).getBody();
		} catch (RestClientException e) {
			log.info("Rest client exception");
			throw new RestClientException("Rest client error" + e);
		}

	}

	public LiveEventResponse meLiveEvents() {

//		HttpHeaders headers = headers(token);
//		HttpEntity<String> entity = new HttpEntity<String>(headers);

		try {
			return restTemplate.exchange(meLiveEventUrl, HttpMethod.GET, null, LiveEventResponse.class).getBody();
		} catch (RestClientException e) {
			log.info("Rest client exception");
			throw new RestClientException("Rest client error" + e);
		}

	}

//	private HttpHeaders headers() {
//		HttpHeaders headers = new HttpHeaders();
//		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
//		headers.setContentType(MediaType.APPLICATION_JSON);
//		headers.setBearerAuth(token);
//		headers.set("Accept", "application/vnd.vimeo.*+json;version=3.4");
//		return headers;
//	}

}
