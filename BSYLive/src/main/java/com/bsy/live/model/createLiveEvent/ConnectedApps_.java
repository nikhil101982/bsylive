package com.bsy.live.model.createLiveEvent;

import java.io.Serializable;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"all_scopes",
"is_connected",
"needed_scopes",
"options",
"uri"
})
public class ConnectedApps_ implements Serializable
{

@JsonProperty("all_scopes")
public List<Object> allScopes = null;
@JsonProperty("is_connected")
public Boolean isConnected;
@JsonProperty("needed_scopes")
public List<Object> neededScopes = null;
@JsonProperty("options")
public List<String> options = null;
@JsonProperty("uri")
public String uri;
private final static long serialVersionUID = 2163339524018969416L;

}