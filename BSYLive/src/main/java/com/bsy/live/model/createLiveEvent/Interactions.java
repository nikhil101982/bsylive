package com.bsy.live.model.createLiveEvent;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"add_privacy_user",
"block",
"connected_apps",
"follow",
"report"
})
public class Interactions implements Serializable
{

@JsonProperty("add_privacy_user")
public AddPrivacyUser addPrivacyUser;
@JsonProperty("block")
public Blocks block;
@JsonProperty("connected_apps")
public ConnectedApps_ connectedApps;
@JsonProperty("follow")
public Follow follow;
@JsonProperty("report")
public Report report;
private final static long serialVersionUID = -2112912302537946956L;

}