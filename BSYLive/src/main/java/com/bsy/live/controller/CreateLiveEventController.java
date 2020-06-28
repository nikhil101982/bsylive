
package com.bsy.live.controller;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.bsy.live.model.createLiveEvent.CreateLiveEventResponse;
import com.bsy.live.service.CreateLiveEventService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/v1/live")
public class CreateLiveEventController {

	private static final String AUTH_HEADER = "Authorization";

	@Autowired
	CreateLiveEventService createLiveEventService;

	@GetMapping(value = "/events/{user_id}")
	public ResponseEntity<CreateLiveEventResponse> createEvent(@RequestHeader(value = AUTH_HEADER) String token,
			@PathParam("user_id") String userId) throws Exception {

		CreateLiveEventResponse createLiveEventResponse = createLiveEventService.createLiveEvent(token , userId);
		
		log.debug("operation=create live event, Returning CreateLiveEventResponse=" + createLiveEventResponse);
		
		return new ResponseEntity<CreateLiveEventResponse>(createLiveEventResponse, HttpStatus.OK);

	}

}
