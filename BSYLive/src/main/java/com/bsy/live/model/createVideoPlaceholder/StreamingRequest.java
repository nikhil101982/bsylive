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
"live"
})
@Setter
@Getter
@AllArgsConstructor
public class StreamingRequest implements Serializable
{

@JsonProperty("live")
public Live live;
private final static long serialVersionUID = -863507011824299882L;

}
