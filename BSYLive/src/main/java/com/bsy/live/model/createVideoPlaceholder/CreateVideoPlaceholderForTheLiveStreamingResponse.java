package com.bsy.live.model.createVideoPlaceholder;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "uri", "name", "description", "link" })
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateVideoPlaceholderForTheLiveStreamingResponse implements Serializable {

	@JsonProperty("uri")
	public String uri;
	@JsonProperty("name")
	public String name;
	@JsonProperty("description")
	public String description;
	@JsonProperty("link")
	public String link;
	private final static long serialVersionUID = -6877749602109031415L;

}
