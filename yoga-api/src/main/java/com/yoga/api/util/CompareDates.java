package com.yoga.api.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class CompareDates {

	public static String compareCourseStartDate(String courseStartDateString) throws ParseException {

		String result = "false";

		String[] startDateStringArray = courseStartDateString.split("-");

		int courseStartDateStringSize = startDateStringArray.length;

		int[] startDateIntArray = new int[courseStartDateStringSize];

		for (int i = 0; i < courseStartDateStringSize; i++) {

			startDateIntArray[i] = Integer.parseInt(startDateStringArray[i]);
		}

		LocalDate startDate = LocalDate.of(startDateIntArray[0], startDateIntArray[1], startDateIntArray[2]);

		LocalDate currentDate = LocalDate.now();

		if (startDate.compareTo(currentDate) > 0 || startDate.compareTo(currentDate) == 0) {
			result = "true";
		}

		return result;

	}

}
