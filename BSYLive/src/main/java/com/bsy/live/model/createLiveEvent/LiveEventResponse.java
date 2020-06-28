package com.bsy.live.model.createLiveEvent;

import java.io.Serializable;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"album",
"automatically_title_stream",
"chat_enabled",
"created_time",
"embed",
"from_showcase",
"link",
"live_clips",
"live_destinations",
"metadata",
"next_occurrence_time",
"pictures",
"playlist_sort",
"privacy",
"rtmp_link",
"schedule",
"stream_description",
"stream_key",
"stream_password",
"stream_privacy",
"stream_title",
"streamable_clip",
"time_zone",
"title",
"uri",
"user"
})
public class LiveEventResponse implements Serializable
{

@JsonProperty("album")
public List<String> album = null;
@JsonProperty("automatically_title_stream")
public Boolean automaticallyTitleStream;
@JsonProperty("chat_enabled")
public Boolean chatEnabled;
@JsonProperty("created_time")
public String createdTime;
@JsonProperty("embed")
public Embed embed;
@JsonProperty("from_showcase")
public String fromShowcase;
@JsonProperty("link")
public String link;
@JsonProperty("live_clips")
public List<String> liveClips = null;
@JsonProperty("live_destinations")
public List<String> liveDestinations = null;
@JsonProperty("metadata")
public Metadata metadata;
@JsonProperty("next_occurrence_time")
public String nextOccurrenceTime;
@JsonProperty("pictures")
public Pictures_ pictures;
@JsonProperty("playlist_sort")
public String playlistSort;
@JsonProperty("privacy")
public Privacy privacy;
@JsonProperty("rtmp_link")
public String rtmpLink;
@JsonProperty("schedule")
public Schedule schedule;
@JsonProperty("stream_description")
public String streamDescription;
@JsonProperty("stream_key")
public String streamKey;
@JsonProperty("stream_password")
public String streamPassword;
@JsonProperty("stream_privacy")
public StreamPrivacy streamPrivacy;
@JsonProperty("stream_title")
public String streamTitle;
@JsonProperty("streamable_clip")
public List<Object> streamableClip = null;
@JsonProperty("time_zone")
public String timeZone;
@JsonProperty("title")
public String title;
@JsonProperty("uri")
public String uri;
@JsonProperty("user")
public User user;
private final static long serialVersionUID = -8382587863372187319L;

}