package com.yoga.api.model;

import java.util.ArrayList;
import java.util.List;

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
public class CourseWithDay {

	
	private String courseName;
	private int couseDuration;
	private String startDate;
	private Integer courseId;
	private List<CourseDays> courseDays = new ArrayList<CourseDays>();

}
