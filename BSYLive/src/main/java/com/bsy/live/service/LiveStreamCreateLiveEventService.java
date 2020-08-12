package com.bsy.live.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import com.bsy.live.model.livestream.createLiveEvent.LivestreamCreateLiveEventRequest;
import com.bsy.live.model.livestream.createLiveEvent.LivestreamCreateLiveEventResponse;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class LiveStreamCreateLiveEventService {

	RestTemplate restTemplate;

	@Value("${vimeo.livestream.events.me.url}")
	String createLiveEventsUrl;

	public LivestreamCreateLiveEventResponse createLiveEvent(String accountId,
			LivestreamCreateLiveEventRequest livestreamCreateLiveEventRequest) {

		HttpHeaders headers = headers();
		HttpEntity<?> httpEntity = new HttpEntity<Object>(livestreamCreateLiveEventRequest, headers);

		Map<String, String> params = new HashMap<String, String>();
		params.put("accountId", accountId);

		try {
			return restTemplate.exchange(createLiveEventsUrl, HttpMethod.POST, httpEntity,
					LivestreamCreateLiveEventResponse.class, params).getBody();
		} catch (RestClientException e) {
			log.info("Rest client exception");
			throw new RestClientException("Rest client error" + e);
		}

	}

	private HttpHeaders headers() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		return headers;
	}

}
