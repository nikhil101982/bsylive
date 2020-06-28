package com.bsy.live.model.createLiveEvent;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"privacy"
})
public class Videos__ implements Serializable
{

@JsonProperty("privacy")
public Privacy_ privacy;
private final static long serialVersionUID = -580095040992648565L;

}