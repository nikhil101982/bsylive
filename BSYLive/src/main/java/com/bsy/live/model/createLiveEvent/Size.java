package com.bsy.live.model.createLiveEvent;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"height",
"link",
"link_with_play_button",
"width"
})
public class Size implements Serializable
{

@JsonProperty("height")
public Integer height;
@JsonProperty("link")
public String link;
@JsonProperty("link_with_play_button")
public String linkWithPlayButton;
@JsonProperty("width")
public Integer width;
private final static long serialVersionUID = -2738406710168880030L;

}