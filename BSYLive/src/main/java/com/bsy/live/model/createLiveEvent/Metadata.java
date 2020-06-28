package com.bsy.live.model.createLiveEvent;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"connections"
})
public class Metadata implements Serializable
{

@JsonProperty("connections")
public Connections connections;
private final static long serialVersionUID = 1461997315982674341L;

}