package com.bsy.live.model.createLiveEvent;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"add",
"comments",
"download",
"embed",
"password",
"view"
})
public class Privacy_ implements Serializable
{

@JsonProperty("add")
public Boolean add;
@JsonProperty("comments")
public String comments;
@JsonProperty("download")
public Boolean download;
@JsonProperty("embed")
public String embed;
@JsonProperty("password")
public String password;
@JsonProperty("view")
public String view;
private final static long serialVersionUID = 6254864363598571146L;

}