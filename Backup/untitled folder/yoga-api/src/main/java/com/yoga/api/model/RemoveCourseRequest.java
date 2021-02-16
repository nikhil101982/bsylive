
package com.yoga.api.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.lang.Nullable;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RemoveCourseRequest implements Serializable {

	@Nullable
	public String courseName;
	public String couseDuration;
	public String startDate;
	public Integer courseId;

}
