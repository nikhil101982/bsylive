package com.yoga.api.model;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DaysResponse {

	String dayName;
	Integer dayId;
	LocalDate date;


}
