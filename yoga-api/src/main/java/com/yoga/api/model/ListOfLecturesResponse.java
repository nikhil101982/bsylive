package com.yoga.api.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ListOfLecturesResponse {

	String status;
	String message;

	List<ListOfLectures> lecture;

}
