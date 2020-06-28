package com.bsy.live.model.createLiveEvent;

import java.io.Serializable;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"added",
"added_time",
"options",
"uri"
})
public class Blocks implements Serializable
{

@JsonProperty("added")
public Boolean added;
@JsonProperty("added_time")
public String addedTime;
@JsonProperty("options")
public List<String> options = null;
@JsonProperty("uri")
public String uri;
private final static long serialVersionUID = -388054848677951492L;

}