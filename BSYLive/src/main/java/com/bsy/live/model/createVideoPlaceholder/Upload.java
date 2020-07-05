package com.bsy.live.model.createVideoPlaceholder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "approach" })
@Getter
@Setter
@AllArgsConstructor
public class Upload implements Serializable {

	@JsonProperty("approach")
	public String approach;
	private final static long serialVersionUID = 6739071089144817629L;

}
