package com.bsy.live.model.createLiveEvent;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "description", "link", "name", "type" })
public class Website implements Serializable {

	private final static long serialVersionUID = -7422246767417491293L;

	@JsonProperty("description")
	public String description;
	@JsonProperty("link")
	public String link;
	@JsonProperty("name")
	public String name;
	@JsonProperty("type")
	public String type;

}