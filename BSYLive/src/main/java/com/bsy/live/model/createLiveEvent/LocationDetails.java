package com.bsy.live.model.createLiveEvent;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"city",
"country",
"country_iso_code",
"formatted_address",
"latitude",
"longitude",
"neighborhood",
"state",
"state_iso_code",
"sub_locality"
})
public class LocationDetails implements Serializable
{

@JsonProperty("city")
public String city;
@JsonProperty("country")
public String country;
@JsonProperty("country_iso_code")
public String countryIsoCode;
@JsonProperty("formatted_address")
public String formattedAddress;
@JsonProperty("latitude")
public Float latitude;
@JsonProperty("longitude")
public Float longitude;
@JsonProperty("neighborhood")
public String neighborhood;
@JsonProperty("state")
public String state;
@JsonProperty("state_iso_code")
public String stateIsoCode;
@JsonProperty("sub_locality")
public String subLocality;
private final static long serialVersionUID = 4531302756368326689L;

}