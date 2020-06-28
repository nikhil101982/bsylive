package com.bsy.live.model.createLiveEvent;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"free",
"max",
"used"
})
public class Lifetime implements Serializable
{

@JsonProperty("free")
public Integer free;
@JsonProperty("max")
public Integer max;
@JsonProperty("used")
public Integer used;
private final static long serialVersionUID = 3901391726403764251L;

}