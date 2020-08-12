
package com.bsy.live.controller;

import java.io.IOException;
import java.util.Base64;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bsy.live.model.livestream.createLiveEvent.LivestreamCreateLiveEventRequest;
import com.bsy.live.model.livestream.createLiveEvent.LivestreamCreateLiveEventResponse;
import com.bsy.live.service.LiveStreamCreateLiveEventService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/v1/livestream")
public class LivestreamCreateLiveEventController {

	@Autowired
	LiveStreamCreateLiveEventService liveEventService;
	
	@Value("${vimeo.live.api.access.token}")
	String basicAuthString;

	@PostMapping(value = "/event/{accountId}")
	public ResponseEntity<LivestreamCreateLiveEventResponse> createEvent(@PathParam("accountId") String accountId,
			@RequestBody LivestreamCreateLiveEventRequest livestreamCreateLiveEventRequest) throws Exception {

		if (isUserAuthenticated(basicAuthString)) {
			LivestreamCreateLiveEventResponse livestreamCreateLiveEventResponse = liveEventService
					.createLiveEvent(accountId, livestreamCreateLiveEventRequest);

			log.debug("operation=create live livestream event, Returning LiveEventResponse="
					+ livestreamCreateLiveEventResponse);

			return new ResponseEntity<LivestreamCreateLiveEventResponse>(livestreamCreateLiveEventResponse,
					HttpStatus.OK);

		} else {
			return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
		}

	}

	private boolean isUserAuthenticated(String authString) throws IOException {

		String decodedAuth = "";
		// Header is in the format "Basic 5tyc0uiDat4"
		// We need to extract data before decoding it back to original string
		String[] authParts = authString.split("\\s+");
		String authInfo = authParts[1];
		// Decode the data back to original string
		byte[] bytes = null;
		bytes = Base64.getDecoder().decode(authInfo);
		decodedAuth = new String(bytes);
		System.out.println(decodedAuth);

		/**
		 * include logic to validate user authentication. it can be using ldap, or token
		 * exchange mechanism or your custom authentication mechanism.
		 */

		return true;
	}

}
