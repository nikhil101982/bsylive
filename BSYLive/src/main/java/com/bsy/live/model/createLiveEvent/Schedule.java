package com.bsy.live.model.createLiveEvent;

import java.io.Serializable;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"daily_time",
"scheduled_time",
"start_time",
"type",
"weekdays"
})
public class Schedule implements Serializable
{

@JsonProperty("daily_time")
public String dailyTime;
@JsonProperty("scheduled_time")
public String scheduledTime;
@JsonProperty("start_time")
public String startTime;
@JsonProperty("type")
public String type;
@JsonProperty("weekdays")
public List<String> weekdays = null;
private final static long serialVersionUID = 5020277283258694921L;

}