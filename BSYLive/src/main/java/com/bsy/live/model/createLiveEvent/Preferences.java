package com.bsy.live.model.createLiveEvent;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"videos"
})
public class Preferences implements Serializable
{

@JsonProperty("videos")
public Videos__ videos;
private final static long serialVersionUID = -2163589138991995101L;

}