package com.bsy.live.model.createLiveEvent;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"live_video",
"pictures",
"pre_live_video",
"videos"
})
public class Connections implements Serializable
{

@JsonProperty("live_video")
public LiveVideo liveVideo;
@JsonProperty("pictures")
public Pictures pictures;
@JsonProperty("pre_live_video")
public PreLiveVideo preLiveVideo;
@JsonProperty("videos")
public Videos videos;
private final static long serialVersionUID = -1530650825860136321L;

}