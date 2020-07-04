package com.bsy.live.model.livestream.createLiveEvent;

import java.io.Serializable;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Component
public class LivestreamCreateLiveEventRequest implements Serializable {

	private final static long serialVersionUID = -6348775071387716991L;

	@JsonProperty("full_name")
	private String fullName;

}
