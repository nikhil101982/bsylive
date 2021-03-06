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
public class AllCourses {
	
	private String courseName;
	private Integer courseId;
	private String language;
	private String startDate;
	private String endDate;
	private String days;

	
}
