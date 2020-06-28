package com.bsy.live.model.createLiveEvent;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"active",
"link",
"sticky",
"url",
"use_link"
})
public class Custom implements Serializable
{

@JsonProperty("active")
public Boolean active;
@JsonProperty("link")
public String link;
@JsonProperty("sticky")
public Boolean sticky;
@JsonProperty("url")
public String url;
@JsonProperty("use_link")
public Boolean useLink;
private final static long serialVersionUID = -9116042588075744578L;

}