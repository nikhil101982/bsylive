package com.bsy.live.model.checkUserQuota;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"monthly_remaining",
"monthly_maximum",
"event_maximum"
})
public class Time implements Serializable
{

@JsonProperty("monthly_remaining")
public Integer monthlyRemaining;
@JsonProperty("monthly_maximum")
public Integer monthlyMaximum;
@JsonProperty("event_maximum")
public Integer eventMaximum;
private final static long serialVersionUID = -5119342575022482642L;

}
