
package com.bsy.live.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bsy.live.model.checkUserQuota.CheckUserQuotaResponse;
import com.bsy.live.service.CheckUserQuotaService;

import lombok.extern.slf4j.Slf4j;

/**
 */

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/v1/live")
public class CheckUserQuotaController {

	@Autowired
	CheckUserQuotaService checkYourEndUserQuotaService;
	
	@Value("${vimeo.live.api.access.token}")
	String token;

	@GetMapping(value = "/liveQuota")
	public ResponseEntity<CheckUserQuotaResponse> checkUserQuota()
			throws Exception {
		

		CheckUserQuotaResponse userQuotaResponse = checkYourEndUserQuotaService.checkQuota(token);
		
		log.debug("operation=checking end user's live quota, Returning CheckUserQuotaResponse=" + userQuotaResponse);
		
		return new ResponseEntity<CheckUserQuotaResponse>(userQuotaResponse, HttpStatus.OK);

	}

}
