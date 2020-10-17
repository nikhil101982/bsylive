package com.yoga.api.model.add;

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
public class AddCourseResponse {

	
	public String courseName;
	public String couseDuration;
	public String startDate;
	public String message;
}
