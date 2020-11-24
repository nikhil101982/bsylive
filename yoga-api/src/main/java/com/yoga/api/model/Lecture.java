package com.yoga.api.model;

import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class Lecture {

	String lectureName;
	String startTime;
	String endTime;
	String currentDate;
	String disableJoinBtn;
	Integer lecId;


}
