package com.bsy.live.model.livestream.createLiveEvent;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "total"})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Likes implements Serializable {
	
	private final static long serialVersionUID = -6348779375387716991L;

	@JsonProperty("total")
	private float total;
}
