package com.bsy.live.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import com.bsy.live.model.createVideoPlaceholder.CreateVideoPlaceholderForTheLiveStreamingRequest;
import com.bsy.live.model.createVideoPlaceholder.CreateVideoPlaceholderForTheLiveStreamingResponse;
import com.bsy.live.model.createVideoPlaceholder.Live;
import com.bsy.live.model.createVideoPlaceholder.LiveRequest;
import com.bsy.live.model.createVideoPlaceholder.LiveResponse;
import com.bsy.live.util.CreateHttpHeaders;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class LiveStreamingService {

	RestTemplate restTemplate;

	@Value("${vimeo.placeholder.url}")
	String videoPlaceHolderUrl;

	@Value("${vimeo.api.version}")
	String apiVersion;

	@Autowired
	CreateHttpHeaders createHttpHeaders;

	@Value("${vimeo.send.the.live.stream.url}")
	String liveStreamUrl;

	public CreateVideoPlaceholderForTheLiveStreamingResponse createVideoPlaceholderForTheLiveStream(
			HttpEntity<?> entity) {

		try {
			return restTemplate.exchange(videoPlaceHolderUrl, HttpMethod.POST, entity,
					CreateVideoPlaceholderForTheLiveStreamingResponse.class).getBody();

		} catch (RestClientException e) {
			log.info("Rest client exception for create video placeholder for the live streaming api");
			throw new RestClientException(
					"Rest client exception for create video placeholder for the live streaming api error" + e);
		}

	}

	public LiveResponse sendLiveStream(String token, CreateVideoPlaceholderForTheLiveStreamingRequest request) {

		HttpHeaders headers = createHttpHeaders.headers(token);
		HttpEntity<?> entity = new HttpEntity<Object>(request, headers);

		CreateVideoPlaceholderForTheLiveStreamingResponse videoPlaceHolder = createVideoPlaceholderForTheLiveStream(
				entity);

		Live live = new Live("ready");
		LiveRequest liveRequest = new LiveRequest(live);
		HttpEntity<?> httpEntity = new HttpEntity<Object>(liveRequest, headers);

		try {

			liveStreamUrl.concat(videoPlaceHolder.getLink());
			return restTemplate.exchange(liveStreamUrl, HttpMethod.PATCH, httpEntity, LiveResponse.class).getBody();

		} catch (RestClientException e) {
			log.info("Rest client exception for send live streaming api");
			throw new RestClientException("Rest client exception for send live streaming api error" + e);
		}

	}

}
