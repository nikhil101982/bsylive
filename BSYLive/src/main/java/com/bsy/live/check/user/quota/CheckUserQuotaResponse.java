package com.bsy.live.check.user.quota;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Getter;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "live_quota" })
@Getter
@Setter
public class CheckUserQuotaResponse implements Serializable {

	@JsonProperty("live_quota")
	public LiveQuota liveQuota;
	private final static long serialVersionUID = -6348775375387716991L;

}
