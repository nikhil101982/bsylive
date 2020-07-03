package com.bsy.live.model.livestream.createLiveEvent;

import java.io.Serializable;
import java.util.List;

import com.bsy.live.model.createLiveEvent.Likes;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "id", "logo", "description", "likes", "fullName", "shortName", "ownerAccountId", "viewerCount",
		"createdAt", "startTime", "endTime", "tags", "isPublic", "isSearchable", "viewerCountVisible",
		"postCommentsEnabled", "liveChatEnabled", "isEmbeddable", "isPasswordProtected", "isWhiteLabeled",
		"embedRestriction", "embedRestrictionWhitelist", "embedRestrictionBlacklist", "isLive" })
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LivestreamCreateLiveEventResponse implements Serializable {

	private final static long serialVersionUID = -6348775371387716991L;

	@JsonProperty("id")
	private float id;

	@JsonProperty("logo")
	Logo logo;

	@JsonProperty("description")
	private String description;

	@JsonProperty("likes")
	Likes likes;

	@JsonProperty("fullName")
	private String fullName;

	@JsonProperty("shortName")
	private String shortName;

	@JsonProperty("ownerAccountId")
	private float ownerAccountId;

	@JsonProperty("viewerCount")
	private float viewerCount;

	@JsonProperty("createdAt")
	private String createdAt;

	@JsonProperty("startTime")
	private String startTime;

	@JsonProperty("endTime")
	private String endTime;

	@JsonProperty("tags")
	private boolean draft;
	List<Object> tags;

	@JsonProperty("isPublic")
	private boolean isPublic;

	@JsonProperty("isSearchable")
	private boolean isSearchable;

	@JsonProperty("viewerCountVisible")
	private boolean viewerCountVisible;

	@JsonProperty("postCommentsEnabled")
	private boolean postCommentsEnabled;

	@JsonProperty("liveChatEnabled")
	private boolean liveChatEnabled;

	@JsonProperty("isEmbeddable")
	private boolean isEmbeddable;

	@JsonProperty("isPasswordProtected")
	private boolean isPasswordProtected;

	@JsonProperty("isWhiteLabeled")
	private boolean isWhiteLabeled;

	@JsonProperty("embedRestriction")
	private String embedRestriction;

	@JsonProperty("embedRestrictionWhitelist")
	List<Object> embedRestrictionWhitelist;

	@JsonProperty("embedRestrictionBlacklist")
	private String embedRestrictionBlacklist;

	@JsonProperty("isLive")
	private boolean isLive;

}
