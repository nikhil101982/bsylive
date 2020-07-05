package com.bsy.live.model.createVideoPlaceholder;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"id",
"link",
"key",
"active_time",
"ended_time",
"archived_time",
"status",
"scheduled_start_time",
"seconds_remaining",
"session_id"
})
@Setter
@Getter
@AllArgsConstructor
public class SendLiveStream implements Serializable {

	@JsonProperty("id")
	public Integer id;
	@JsonProperty("link")
	public String link;
	@JsonProperty("key")
	public String key;
	@JsonProperty("active_time")
	public Object activeTime;
	@JsonProperty("ended_time")
	public Object endedTime;
	@JsonProperty("archived_time")
	public Object archivedTime;
	@JsonProperty("status")
	public String status;
	@JsonProperty("scheduled_start_time")
	public Object scheduledStartTime;
	@JsonProperty("seconds_remaining")
	public Integer secondsRemaining;
	@JsonProperty("session_id")
	public String sessionId;
	private final static long serialVersionUID = 4141594393924886041L;

}
