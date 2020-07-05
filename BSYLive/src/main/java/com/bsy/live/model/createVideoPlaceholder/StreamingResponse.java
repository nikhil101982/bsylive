package com.bsy.live.model.createVideoPlaceholder;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Setter
@Getter
@AllArgsConstructor
public class StreamingResponse implements Serializable {

	@JsonProperty("live")
	public SendLiveStream live;
	private final static long serialVersionUID = -4226081318374336106L;

}
