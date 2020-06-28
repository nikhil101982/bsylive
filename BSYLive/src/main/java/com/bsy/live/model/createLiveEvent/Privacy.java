package com.bsy.live.model.createLiveEvent;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"embed",
"unlisted_hash"
})
public class Privacy implements Serializable
{

@JsonProperty("embed")
public String embed;
@JsonProperty("unlisted_hash")
public String unlistedHash;
private final static long serialVersionUID = -7506714221526871515L;

}