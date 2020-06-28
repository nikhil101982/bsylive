package com.bsy.live.model.createLiveEvent;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"free",
"max",
"reset_date",
"used"
})
public class Periodic implements Serializable
{

@JsonProperty("free")
public Integer free;
@JsonProperty("max")
public Integer max;
@JsonProperty("reset_date")
public String resetDate;
@JsonProperty("used")
public Integer used;
private final static long serialVersionUID = -8196158201293596671L;

}