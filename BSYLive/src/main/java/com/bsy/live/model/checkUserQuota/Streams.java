package com.bsy.live.model.checkUserQuota;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Getter;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "remaining", "maximum" })
@Getter
@Setter
public class Streams implements Serializable {

	@JsonProperty("remaining")
	public Integer remaining;
	@JsonProperty("maximum")
	public Integer maximum;
	private final static long serialVersionUID = 9032536180156192665L;

}