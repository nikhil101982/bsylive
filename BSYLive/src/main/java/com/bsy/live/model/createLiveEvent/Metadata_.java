
package com.bsy.live.model.createLiveEvent;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"connections",
"interactions"
})
public class Metadata_ implements Serializable
{

@JsonProperty("connections")
public Connection connections;
@JsonProperty("interactions")
public Interactions interactions;
private final static long serialVersionUID = -5630415550417983872L;

}