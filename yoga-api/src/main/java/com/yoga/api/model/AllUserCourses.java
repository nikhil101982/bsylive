package com.yoga.api.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AllUserCourses {
	
	private String courseName;
	private int days;
	private String startDate;
	private Integer courseId;
	private String language;
	private String endDate;
	
	private int courseDuration;




	
}
