package com.bsy.live.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bsy.live.model.createVideoPlaceholder.CreateVideoPlaceholderForTheLiveStreamingRequest;
import com.bsy.live.model.createVideoPlaceholder.CreateVideoPlaceholderForTheLiveStreamingResponse;
import com.bsy.live.model.createVideoPlaceholder.StreamingResponse;
import com.bsy.live.service.LiveStreamingService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/v1/live")
public class EndLiveStreamingController {

	private static final String AUTH_HEADER = "Authorization";

	@Autowired
	LiveStreamingService liveStreamingService;

	/*
	 * End of live streaming
	 */

	/**
	 * @param token
	 * @param
	 * @return LiveResponse
	 * @throws Exception
	 */

	@PostMapping(value = "/end/liveStreaming")
	public void endLiveStreaming(@RequestHeader(value = AUTH_HEADER) String token) throws Exception {

		liveStreamingService.endLiveStream(token);
		log.debug("operation= End Live streaming");

	}

}
