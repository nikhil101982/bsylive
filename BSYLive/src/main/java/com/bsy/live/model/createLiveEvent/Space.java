package com.bsy.live.model.createLiveEvent;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"free",
"max",
"showing",
"used"
})
public class Space implements Serializable
{

@JsonProperty("free")
public Integer free;
@JsonProperty("max")
public Integer max;
@JsonProperty("showing")
public String showing;
@JsonProperty("used")
public Integer used;
private final static long serialVersionUID = 9182589900575267902L;

}