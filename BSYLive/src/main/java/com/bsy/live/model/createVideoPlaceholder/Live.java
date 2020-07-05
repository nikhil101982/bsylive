package com.bsy.live.model.createVideoPlaceholder;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"status"
})
@Setter
@Getter
@AllArgsConstructor
public class Live implements Serializable
{

@JsonProperty("status")
public String status;
private final static long serialVersionUID = -5223387982707139354L;

}
