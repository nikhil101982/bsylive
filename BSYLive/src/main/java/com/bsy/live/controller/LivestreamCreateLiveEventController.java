
package com.bsy.live.controller;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bsy.live.model.livestream.createLiveEvent.LivestreamCreateLiveEventResponse;
import com.bsy.live.service.LiveStreamCreateLiveEventService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/v1/livestream")
public class LivestreamCreateLiveEventController {

	@Autowired
	LiveStreamCreateLiveEventService liveEventService;

	@PostMapping(value = "/event/{accountId}")
	public ResponseEntity<LivestreamCreateLiveEventResponse> createEvent(@PathParam("accountId") String accountId)
			throws Exception {

		LivestreamCreateLiveEventResponse livestreamCreateLiveEventResponse = liveEventService.createLiveEvent(accountId);

		log.debug("operation=create live livestream event, Returning LiveEventResponse="
				+ livestreamCreateLiveEventResponse);

		return new ResponseEntity<LivestreamCreateLiveEventResponse>(livestreamCreateLiveEventResponse, HttpStatus.OK);

	}

}
