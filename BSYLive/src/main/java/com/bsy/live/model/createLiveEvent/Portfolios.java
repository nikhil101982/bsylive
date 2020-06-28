package com.bsy.live.model.createLiveEvent;

import java.io.Serializable;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "options", "total", "uri" })
public class Portfolios implements Serializable {

	@JsonProperty("options")
	public List<String> options = null;
	@JsonProperty("total")
	public Integer total;
	@JsonProperty("uri")
	public String uri;
	private final static long serialVersionUID = 8776045270709334363L;
}