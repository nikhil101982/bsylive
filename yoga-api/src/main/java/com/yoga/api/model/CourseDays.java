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
public class CourseDays {


	private Integer dayId;
	private List<Lecture> lecture = new ArrayList<Lecture>();
	private String dayName;

}
