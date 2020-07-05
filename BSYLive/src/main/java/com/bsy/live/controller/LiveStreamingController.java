package com.bsy.live.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@CrossOrigin
@RestController
@RequestMapping("/v1/live")
public class LiveStreamingController {

	private static final String AUTH_HEADER = "Authorization";

	@Autowired
	LiveStreamingService liveStreamingService;

	/*
	 * 2 api's
	 * 
	 * 1. Create a video placeholder for the live stream 
	 * 2.Send the live stream to
	 * the video placeholder
	 */

	/*
	 * The API responds with HTTP status 200 and the complete video representation.
	 * in response look for the live field, and specifically for live.link and
	 * live.key: The live.link field gives the RTMP URL of your live video, while
	 * live.key gives the stream key. Need both of these values to point the live
	 * stream in the right direction, which control from the live streaming app on
	 * computer. Open the stream settings in this app, and supply your RTMP URL and
	 * stream key.
	 */

	/**
	 * @param token
	 * @param CreateVideoPlaceholderForTheLiveStreamingRequest
	 * @return LiveResponse
	 * @throws Exception
	 */

	@PostMapping(value = "/liveStreaming")
	public ResponseEntity<StreamingResponse> liveStream(@RequestHeader(value = AUTH_HEADER) String token) throws Exception {

		StreamingResponse liveResponse = liveStreamingService.sendLiveStream(token);

		log.debug("operation= Live streaming, Returning LiveResponse=" + liveResponse);

		return new ResponseEntity<StreamingResponse>(liveResponse, HttpStatus.OK);

	}

}
