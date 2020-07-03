package com.bsy.live.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.bsy.live.model.livestream.createLiveEvent.LivestreamCreateLiveEventResponse;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class LiveStreamCreateLiveEventService {

	RestTemplate restTemplate;

	@Value("${vimeo.livestream.events.me.url}")
	String createLiveEventsUrl;



	public LivestreamCreateLiveEventResponse createLiveEvent(String accountId) {

//		HttpHeaders headers = headers(token);
//		HttpEntity<String> entity = new HttpEntity<String>(headers);
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("accountId", accountId);


		try {
			return restTemplate.exchange(createLiveEventsUrl, HttpMethod.POST, null, LivestreamCreateLiveEventResponse.class,params)
					.getBody();
		} catch (RestClientException e) {
			log.info("Rest client exception");
			throw new RestClientException("Rest client error" + e);
		}

	}


//	private HttpHeaders headers(String token) {
//		HttpHeaders headers = new HttpHeaders();
//		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
//		headers.setContentType(MediaType.APPLICATION_JSON);
//		headers.setBearerAuth(token);
//		headers.set("Accept", "application/vnd.vimeo.*+json;version=3.4");
//		return headers;
//	}

}
