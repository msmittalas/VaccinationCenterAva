package com.ysm.microservice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"pincode",
"sessions",
"address",
"fee_type",
"long",
"district_name",
"block_name",
"center_id",
"state_name",
"name",
"from",
"to",
"lat"
})
@Generated("jsonschema2pojo")
 class Center {

@JsonProperty("pincode")
private Integer pincode;
@JsonProperty("sessions")
private List<Session> sessions = null;
@JsonProperty("address")
private String address;
@JsonProperty("fee_type")
private String feeType;
@JsonProperty("long")
private Integer _long;
@JsonProperty("district_name")
private String districtName;
@JsonProperty("block_name")
private String blockName;
@JsonProperty("center_id")
private Integer centerId;
@JsonProperty("state_name")
private String stateName;
@JsonProperty("name")
private String name;
@JsonProperty("from")
private String from;
@JsonProperty("to")
private String to;
@JsonProperty("lat")
private Integer lat;
@JsonIgnore
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

@JsonProperty("pincode")
public Integer getPincode() {
return pincode;
}

@JsonProperty("pincode")
public void setPincode(Integer pincode) {
this.pincode = pincode;
}

@JsonProperty("sessions")
public List<Session> getSessions() {
return sessions;
}

@JsonProperty("sessions")
public void setSessions(List<Session> sessions) {
this.sessions = sessions;
}

@JsonProperty("address")
public String getAddress() {
return address;
}

@JsonProperty("address")
public void setAddress(String address) {
this.address = address;
}

@JsonProperty("fee_type")
public String getFeeType() {
return feeType;
}

@JsonProperty("fee_type")
public void setFeeType(String feeType) {
this.feeType = feeType;
}

@JsonProperty("long")
public Integer getLong() {
return _long;
}

@JsonProperty("long")
public void setLong(Integer _long) {
this._long = _long;
}

@JsonProperty("district_name")
public String getDistrictName() {
return districtName;
}

@JsonProperty("district_name")
public void setDistrictName(String districtName) {
this.districtName = districtName;
}

@JsonProperty("block_name")
public String getBlockName() {
return blockName;
}

@JsonProperty("block_name")
public void setBlockName(String blockName) {
this.blockName = blockName;
}

@JsonProperty("center_id")
public Integer getCenterId() {
return centerId;
}

@JsonProperty("center_id")
public void setCenterId(Integer centerId) {
this.centerId = centerId;
}

@JsonProperty("state_name")
public String getStateName() {
return stateName;
}

@JsonProperty("state_name")
public void setStateName(String stateName) {
this.stateName = stateName;
}

@JsonProperty("name")
public String getName() {
return name;
}

@JsonProperty("name")
public void setName(String name) {
this.name = name;
}

@JsonProperty("from")
public String getFrom() {
return from;
}

@JsonProperty("from")
public void setFrom(String from) {
this.from = from;
}

@JsonProperty("to")
public String getTo() {
return to;
}

@JsonProperty("to")
public void setTo(String to) {
this.to = to;
}

@JsonProperty("lat")
public Integer getLat() {
return lat;
}

@JsonProperty("lat")
public void setLat(Integer lat) {
this.lat = lat;
}

@JsonAnyGetter
public Map<String, Object> getAdditionalProperties() {
return this.additionalProperties;
}

@JsonAnySetter
public void setAdditionalProperty(String name, Object value) {
this.additionalProperties.put(name, value);
}

}



@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"date",
"vaccine",
"slots",
"min_age_limit",
"session_id",
"available_capacity"
})
@Generated("jsonschema2pojo")
 class Session {

@JsonProperty("date")
private String date;
@JsonProperty("vaccine")
private String vaccine;
@JsonProperty("slots")
private List<String> slots = null;
@JsonProperty("min_age_limit")
private Integer minAgeLimit;
@JsonProperty("session_id")
private String sessionId;
@JsonProperty("available_capacity")
private Integer availableCapacity;
@JsonIgnore
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

@JsonProperty("date")
public String getDate() {
return date;
}

@JsonProperty("date")
public void setDate(String date) {
this.date = date;
}

@JsonProperty("vaccine")
public String getVaccine() {
return vaccine;
}

@JsonProperty("vaccine")
public void setVaccine(String vaccine) {
this.vaccine = vaccine;
}

@JsonProperty("slots")
public List<String> getSlots() {
return slots;
}

@JsonProperty("slots")
public void setSlots(List<String> slots) {
this.slots = slots;
}

@JsonProperty("min_age_limit")
public Integer getMinAgeLimit() {
return minAgeLimit;
}

@JsonProperty("min_age_limit")
public void setMinAgeLimit(Integer minAgeLimit) {
this.minAgeLimit = minAgeLimit;
}

@JsonProperty("session_id")
public String getSessionId() {
return sessionId;
}

@JsonProperty("session_id")
public void setSessionId(String sessionId) {
this.sessionId = sessionId;
}

@JsonProperty("available_capacity")
public Integer getAvailableCapacity() {
return availableCapacity;
}

@JsonProperty("available_capacity")
public void setAvailableCapacity(Integer availableCapacity) {
this.availableCapacity = availableCapacity;
}

@JsonAnyGetter
public Map<String, Object> getAdditionalProperties() {
return this.additionalProperties;
}

@JsonAnySetter
public void setAdditionalProperty(String name, Object value) {
this.additionalProperties.put(name, value);
}

}



@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"centers"
})
@Generated("jsonschema2pojo")
public class Slots {

@JsonProperty("centers")
private List<Center> centers = null;
@JsonIgnore
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

@JsonProperty("centers")
public List<Center> getCenters() {
return centers;
}

@JsonProperty("centers")
public void setCenters(List<Center> centers) {
this.centers = centers;
}

@JsonAnyGetter
public Map<String, Object> getAdditionalProperties() {
return this.additionalProperties;
}

@JsonAnySetter
public void setAdditionalProperty(String name, Object value) {
this.additionalProperties.put(name, value);
}

}