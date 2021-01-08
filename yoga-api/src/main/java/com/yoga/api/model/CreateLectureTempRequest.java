package com.yoga.api.model;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
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
public class CreateLectureTempRequest {

	private List<DayId> dayIds;
	private String lectureName;
	private String startTime;
	private String endTime;
	private String currentDate;
	private String videoIFrameLink;
	private String liveIFrameLink;
	private Integer courseId;
	
	private String language;
	
	private final static long serialVersionUID = 2177644673242063512L;
	
	private Integer fromDayId;
	private Integer toDayId;


	




}
