package com.yoga.api.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@Data
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class DayByCourseId {

	public Integer dayId;;
	public String dayName;
	public String status;;
	public String message;
	List<LectureByDay> lecture = new ArrayList<>();

	LocalDate date;

	private final static long serialVersionUID = 2177644673242063512L;

}
