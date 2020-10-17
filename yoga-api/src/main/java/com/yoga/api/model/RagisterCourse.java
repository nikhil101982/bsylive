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
public class RagisterCourse {

	
	public String courseName;
	public int couseDuration;
	public String startDate;
	public String userName;
	List<Lecture> lecture = new ArrayList<Lecture>();

}
