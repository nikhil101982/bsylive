package com.bsy.live.model.createLiveEvent;

import java.io.Serializable;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"active",
"link",
"resource_key",
"sizes",
"type",
"uri"
})
public class Pictures___ implements Serializable
{

@JsonProperty("active")
public Boolean active;
@JsonProperty("link")
public String link;
@JsonProperty("resource_key")
public String resourceKey;
@JsonProperty("sizes")
public List<Size_> sizes = null;
@JsonProperty("type")
public String type;
@JsonProperty("uri")
public String uri;
private final static long serialVersionUID = -3625360626864456929L;

}