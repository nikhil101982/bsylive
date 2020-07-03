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
@JsonPropertyOrder({ "url", "thumbnailUrl", "smallUrl" })
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Logo implements Serializable {

	private final static long serialVersionUID = -6348775335387716991L;

	@JsonProperty("url")
	private String url;

	@JsonProperty("thumbnailUrl")
	private String thumbnailUrl;

	@JsonProperty("smallUrl")
	private String smallUrl;

}
