package com.bsy.live.model.createLiveEvent;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"custom",
"vimeo"
})
public class Logos implements Serializable
{

@JsonProperty("custom")
public Custom custom;
@JsonProperty("vimeo")
public Boolean vimeo;
private final static long serialVersionUID = -7582682127771353585L;

}