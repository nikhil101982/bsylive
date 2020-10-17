package com.yoga.api.model;

import java.util.ArrayList;
import java.util.List;

import com.yoga.api.model.add.Day;

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
public class Course {

	
	private String courseName;
	private int couseDuration;
	private String startDate;
	private Integer courseId;
	private List<Day> day = new ArrayList<>();

}
