package com.bsy.live.model.createLiveEvent;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"lifetime",
"periodic",
"space"
})
public class UploadQuota implements Serializable
{

@JsonProperty("lifetime")
public Lifetime lifetime;
@JsonProperty("periodic")
public Periodic periodic;
@JsonProperty("space")
public Space space;
private final static long serialVersionUID = 3035195588454676774L;

}
