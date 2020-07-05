package com.bsy.live.model.createVideoPlaceholder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@Getter
@Setter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "upload" })
public class CreateVideoPlaceholderForTheLiveStreamingRequest implements Serializable {

	@JsonProperty("upload")
	public Upload upload;
	private final static long serialVersionUID = -6278558999361913609L;

}
