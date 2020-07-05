package com.bsy.live.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.bsy.live.model.checkUserQuota.CheckUserQuotaResponse;
import com.bsy.live.util.CreateHttpHeaders;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CheckUserQuotaService {

	RestTemplate restTemplate;

	@Value("${vimeo.check.user.live.quota.url}")
	String url;

	@Autowired
	CreateHttpHeaders createHttpHeaders;

	public CheckUserQuotaResponse checkQuota(String token) {

		HttpHeaders headers = createHttpHeaders.headers(token);
		HttpEntity<String> entity = new HttpEntity<String>(headers);

		try {
			return restTemplate.exchange(url, HttpMethod.GET, entity, CheckUserQuotaResponse.class).getBody();
		} catch (RestClientException e) {
			log.info("Rest client exception");
			throw new RestClientException("Rest client error" + e);
		}

	}

}
