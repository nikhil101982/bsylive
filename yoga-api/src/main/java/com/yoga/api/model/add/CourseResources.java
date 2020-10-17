
package com.yoga.api.model.add;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.lang.Nullable;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CourseResources implements Serializable {

	@Nullable
	public String userName;
	public String courseName;
	public String couseDuration;
	public String startDate;
	public List<Day> day = new ArrayList<Day>();

}
