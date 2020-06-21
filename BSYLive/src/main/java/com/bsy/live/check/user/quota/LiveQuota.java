package com.bsy.live.check.user.quota;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Getter;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "status", "time", "streams" })
@Getter
@Setter
public class LiveQuota implements Serializable {

	@JsonProperty("status")
	public String status;
	@JsonProperty("time")
	public Time time;
	@JsonProperty("streams")
	public Streams streams;
	private final static long serialVersionUID = 9044897801971901528L;

}
