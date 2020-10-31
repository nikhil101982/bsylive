package com.yoga.api.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.yoga.api.model.Day;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class LectureByDay {

	Integer lectureByDayId;
	String lectureName;
	String startTime;
	String endTime;
	String currentDate;
	boolean disableJoinBtn;
	public int sNo;
	String videoIframeDynamicLink;
	String liveIframeDynamicLink;


}


