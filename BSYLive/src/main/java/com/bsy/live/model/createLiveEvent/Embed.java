package com.bsy.live.model.createLiveEvent;

import java.io.Serializable;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"autoplay",
"available_player_logos",
"color",
"embed_chat",
"embed_properties",
"fullscreen_button",
"html",
"like_button",
"logos",
"loop",
"playbar",
"playlist",
"responsiveHtml",
"schedule",
"show_latest_archived_clip",
"title",
"use_color",
"volume"
})
public class Embed implements Serializable
{

@JsonProperty("autoplay")
public Boolean autoplay;
@JsonProperty("available_player_logos")
public List<String> availablePlayerLogos = null;
@JsonProperty("color")
public String color;
@JsonProperty("embed_chat")
public String embedChat;
@JsonProperty("embed_properties")
public List<String> embedProperties = null;
@JsonProperty("fullscreen_button")
public Boolean fullscreenButton;
@JsonProperty("html")
public String html;
@JsonProperty("like_button")
public Boolean likeButton;
@JsonProperty("logos")
public Logos logos;
@JsonProperty("loop")
public Boolean loop;
@JsonProperty("playbar")
public Boolean playbar;
@JsonProperty("playlist")
public Boolean playlist;
@JsonProperty("responsiveHtml")
public String responsiveHtml;
@JsonProperty("schedule")
public Boolean schedule;
@JsonProperty("show_latest_archived_clip")
public Boolean showLatestArchivedClip;
@JsonProperty("title")
public Boolean title;
@JsonProperty("use_color")
public String useColor;
@JsonProperty("volume")
public Boolean volume;
private final static long serialVersionUID = -8449835531870320816L;

}