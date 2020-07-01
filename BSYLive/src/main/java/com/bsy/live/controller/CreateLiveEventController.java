
package com.bsy.live.controller;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bsy.live.model.createLiveEvent.LiveEventResponse;
import com.bsy.live.service.CreateLiveEventService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/v1/live")
public class CreateLiveEventController {

	private static final String AUTH_HEADER = "Authorization";

	@Autowired
	CreateLiveEventService liveEventService;

	@PostMapping(value = "/events/{user_id}")
	public ResponseEntity<LiveEventResponse> createEvent(@RequestHeader(value = AUTH_HEADER) String token,
			@PathParam("user_id") String userId) throws Exception {

		LiveEventResponse liveEventResponse = liveEventService.createLiveEvent(userId);

		log.debug("operation=create live event, Returning LiveEventResponse=" + liveEventResponse);

		return new ResponseEntity<LiveEventResponse>(liveEventResponse, HttpStatus.OK);

	}

	@PostMapping(value = "/events")
	public ResponseEntity<LiveEventResponse> liveEvents(@RequestHeader(value = AUTH_HEADER) String token)
			throws Exception {

		LiveEventResponse liveEventResponse = liveEventService.liveEvents();

		log.debug("operation=live events, Returning LiveEventResponse=" + liveEventResponse);

		return new ResponseEntity<LiveEventResponse>(liveEventResponse, HttpStatus.OK);

	}
	
	@PostMapping(value = "/me/events")
	public ResponseEntity<LiveEventResponse> meLiveEvents(@RequestHeader(value = AUTH_HEADER) String token)
			throws Exception {

		LiveEventResponse liveEventResponse = liveEventService.meLiveEvents();

		log.debug("operation=live events, Returning LiveEventResponse=" + liveEventResponse);

		return new ResponseEntity<LiveEventResponse>(liveEventResponse, HttpStatus.OK);

	}

}
