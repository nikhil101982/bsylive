
package com.bsy.live.controller;

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

	@GetMapping(value = "/liveEvent")
	public ResponseEntity<CreateLiveEventResponse> addEnvelope(@RequestHeader(value = AUTH_HEADER) String token)
			throws Exception {

		CreateLiveEventResponse userQuotaResponse = createLiveEventService.createLiveEvent(token);
		log.debug("operation=checking end user's live quota, Returning CheckUserQuotaResponse=" + userQuotaResponse);
		return new ResponseEntity<CreateLiveEventResponse>(userQuotaResponse, HttpStatus.OK);

	}

}
