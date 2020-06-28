package com.bsy.live.model.createLiveEvent;

import java.io.Serializable;
import java.util.List;
import java.util.prefs.Preferences;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"account",
"available_for_hire",
"bio",
"can_work_remotely",
"content_filter",
"created_time",
"gender",
"link",
"location",
"location_details",
"metadata",
"name",
"pictures",
"preferences",
"resource_key",
"short_bio",
"skills",
"upload_quota",
"uri",
"websites"
})
public class User implements Serializable
{

@JsonProperty("account")
public String account;
@JsonProperty("available_for_hire")
public Boolean availableForHire;
@JsonProperty("bio")
public String bio;
@JsonProperty("can_work_remotely")
public Boolean canWorkRemotely;
@JsonProperty("content_filter")
public List<Object> contentFilter = null;
@JsonProperty("created_time")
public String createdTime;
@JsonProperty("gender")
public String gender;
@JsonProperty("link")
public String link;
@JsonProperty("location")
public String location;
@JsonProperty("location_details")
public LocationDetails locationDetails;
@JsonProperty("metadata")
public Metadata_ metadata;
@JsonProperty("name")
public String name;
@JsonProperty("pictures")
public Pictures___ pictures;
@JsonProperty("preferences")
public Preferences preferences;
@JsonProperty("resource_key")
public String resourceKey;
@JsonProperty("short_bio")
public String shortBio;
@JsonProperty("skills")
public Skills skills;
@JsonProperty("upload_quota")
public UploadQuota uploadQuota;
@JsonProperty("uri")
public String uri;
@JsonProperty("websites")
public List<Website> websites = null;
private final static long serialVersionUID = 6522731074412969158L;

}