
package com.bsy.live.controller;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bsy.live.model.createLiveEvent.LiveEventResponse;
import com.bsy.live.service.CreateLiveEventService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/v1/live")
public class CreateLiveEventController {

	private static final String AUTH_HEADER = "Authorization";

	@Autowired
	CreateLiveEventService liveEventService;

	@PostMapping(value = "/events/{user_id}")
	public ResponseEntity<LiveEventResponse> createEvent(@PathParam("user_id") String userId) throws Exception {

		LiveEventResponse liveEventResponse = liveEventService.createLiveEvent(userId);

		log.debug("operation=create live event, Returning LiveEventResponse=" + liveEventResponse);

		return new ResponseEntity<LiveEventResponse>(liveEventResponse, HttpStatus.OK);

	}



}
