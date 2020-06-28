package com.bsy.live.model.createLiveEvent;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"albums",
"appearances",
"block",
"categories",
"channels",
"connected_apps",
"feed",
"folders",
"followers",
"following",
"groups",
"likes",
"moderated_channels",
"pictures",
"portfolios",
"recommended_channels",
"recommended_users",
"shared",
"videos",
"watched_videos",
"watchlater"
})
public class Connection implements Serializable
{

@JsonProperty("albums")
public Albums albums;
@JsonProperty("appearances")
public Appearances appearances;
@JsonProperty("block")
public Block block;
@JsonProperty("categories")
public Categories categories;
@JsonProperty("channels")
public Channels channels;
@JsonProperty("connected_apps")
public ConnectedApps connectedApps;
@JsonProperty("feed")
public Feed feed;
@JsonProperty("folders")
public Folders folders;
@JsonProperty("followers")
public Followers followers;
@JsonProperty("following")
public Following following;
@JsonProperty("groups")
public Groups groups;
@JsonProperty("likes")
public Likes likes;
@JsonProperty("moderated_channels")
public ModeratedChannels moderatedChannels;
@JsonProperty("pictures")
public Pictures__ pictures;
@JsonProperty("portfolios")
public Portfolios portfolios;
@JsonProperty("recommended_channels")
public RecommendedChannels recommendedChannels;
@JsonProperty("recommended_users")
public RecommendedUsers recommendedUsers;
@JsonProperty("shared")
public Shared shared;
@JsonProperty("videos")
public Videos_ videos;
@JsonProperty("watched_videos")
public WatchedVideos watchedVideos;
@JsonProperty("watchlater")
public Watchlater watchlater;
private final static long serialVersionUID = 1268525095810753747L;

}