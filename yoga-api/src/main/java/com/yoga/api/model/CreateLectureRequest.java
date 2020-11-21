package com.yoga.api.model;

import java.util.ArrayList;
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
public class CreateLectureRequest {

	private List<Integer> dayIds = new ArrayList<>();
	public Integer fromDayId;;
	public Integer toDayId;
	private String lectureName;
	private String startTime;
	private String endTime;
	private String currentDate;
	private String videoIframeDynamicLink;
	private String liveIframeDynamicLink;
	private Integer courseId;
	




}
