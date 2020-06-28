package com.bsy.live.model.createLiveEvent;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"name",
"uri"
})
public class Skills implements Serializable
{

@JsonProperty("name")
public String name;
@JsonProperty("uri")
public String uri;
private final static long serialVersionUID = -6933375260184175598L;

}