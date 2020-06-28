
package com.bsy.live.model.createLiveEvent;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"view"
})
public class StreamPrivacy implements Serializable
{

@JsonProperty("view")
public String view;
private final static long serialVersionUID = -5288690324898283213L;

}
