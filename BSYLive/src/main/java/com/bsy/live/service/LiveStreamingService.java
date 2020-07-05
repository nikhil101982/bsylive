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
import com.bsy.live.model.createVideoPlaceholder.StreamingRequest;
import com.bsy.live.model.createVideoPlaceholder.StreamingResponse;
import com.bsy.live.model.createVideoPlaceholder.Upload;
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

	CreateVideoPlaceholderForTheLiveStreamingResponse videoPlaceHolderResponse;
	
	CreateVideoPlaceholderForTheLiveStreamingRequest request;


	/*
	 * 1. Create a video placeholder for the live stream API
	 */
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

	/*
	 * 2.Send the live stream to the video placeholder
	 */
	public StreamingResponse sendLiveStream(String token) {

		Upload upload = new Upload("live");
		request.setUpload(upload);
		
		HttpHeaders headers = createHttpHeaders.headers(token);
		HttpEntity<?> entity = new HttpEntity<Object>(request, headers);

		videoPlaceHolderResponse = createVideoPlaceholderForTheLiveStream(entity);

		Live live = new Live("ready");
		StreamingRequest liveRequest = new StreamingRequest(live);
		HttpEntity<?> httpEntity = new HttpEntity<Object>(liveRequest, headers);

		try {

			liveStreamUrl.concat(videoPlaceHolderResponse.getLink());
			return restTemplate.exchange(liveStreamUrl, HttpMethod.PATCH, httpEntity, StreamingResponse.class)
					.getBody();

		} catch (RestClientException e) {
			log.info("Rest client exception for send live streaming api");
			throw new RestClientException("Rest client exception for send live streaming api error" + e);
		}

	}

	/*
	 * 3. End live stream API
	 */

	public void endLiveStream(String token) {

		HttpHeaders headers = createHttpHeaders.headers(token);

		Live live = new Live("done");
		StreamingRequest liveRequest = new StreamingRequest(live);
		HttpEntity<?> httpEntity = new HttpEntity<Object>(liveRequest, headers);

		try {

			liveStreamUrl.concat(videoPlaceHolderResponse.getLink());
			restTemplate.exchange(liveStreamUrl, HttpMethod.PATCH, httpEntity, void.class);

		} catch (RestClientException e) {
			log.info("Rest client exception for send live streaming api");
			throw new RestClientException("Rest client exception for send live streaming api error" + e);
		}

	}

}
