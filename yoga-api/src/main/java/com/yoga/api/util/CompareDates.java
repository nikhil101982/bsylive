package com.yoga.api.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CompareDates {

	public static boolean compareCourseStartDate(String courseStartDateString) throws ParseException {

		boolean result = false;
		
		final String DATE_FORMAT = "yyyy-mm-dd";

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT);
		
		Date courseStartDate = simpleDateFormat.parse(courseStartDateString);		
		Date currentDate = simpleDateFormat.parse(simpleDateFormat.format(new Date()));
		
		System.out.println(courseStartDate.compareTo(currentDate) );

		if (courseStartDate.compareTo(currentDate) > 0 || courseStartDate.compareTo(currentDate) == 0) {
			result = true;
			System.out.println(result);
		}

		return result;

	}

}
