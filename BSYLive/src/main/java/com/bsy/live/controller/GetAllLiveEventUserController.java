
package com.bsy.live.controller;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bsy.live.model.createLiveEvent.LiveEventResponse;
import com.bsy.live.service.GetAllLiveEventUserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/v1/live")
public class GetAllLiveEventUserController {

	private static final String AUTH_HEADER = "Authorization";

	@Autowired
	GetAllLiveEventUserService getLiveEventService;

	@GetMapping(value = "/events/{user_id}")
	public ResponseEntity<LiveEventResponse> getEvent(@PathParam("user_id") String userId) throws Exception {

		LiveEventResponse liveEventResponse = getLiveEventService.getAllLiveEvent(userId);

		log.debug("operation=get live event, Returning LiveEventResponse=" + liveEventResponse);

		return new ResponseEntity<LiveEventResponse>(liveEventResponse, HttpStatus.OK);

	}

	@GetMapping(value = "/events")
	public ResponseEntity<LiveEventResponse> liveEvents() throws Exception {

		LiveEventResponse liveEventResponse = getLiveEventService.liveEvents();

		log.debug("operation=live events, Returning LiveEventResponse=" + liveEventResponse);

		return new ResponseEntity<LiveEventResponse>(liveEventResponse, HttpStatus.OK);

	}

	@GetMapping(value = "/me/events")
	public ResponseEntity<LiveEventResponse> meLiveEvents() throws Exception {

		LiveEventResponse liveEventResponse = getLiveEventService.meLiveEvents();

		log.debug("operation=live events, Returning LiveEventResponse=" + liveEventResponse);

		return new ResponseEntity<LiveEventResponse>(liveEventResponse, HttpStatus.OK);

	}

}
