package com.yoga.api.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yoga.api.constant.ApiConstants;
import com.yoga.api.entity.CourseEntity;
import com.yoga.api.entity.DayEntity;
import com.yoga.api.model.DaysResp;
import com.yoga.api.model.DaysResponse;
import com.yoga.api.repository.CourseRepository;
import com.yoga.api.repository.DayRepository;
import com.yoga.api.util.UtilMethods;

@Service
public class GetCourseFindDaysService {

	@Autowired
	CourseRepository courseRepository;

	@Autowired
	DayRepository dayRepository;

	CourseEntity courseEntity;

	DayEntity dayEntity;

	DaysResponse daysResponse;

	List<DaysResponse> daysResponseList;

	UtilMethods utilMethods = new UtilMethods();

	List<LocalDate> datesBetweenStartDateAndEndDate;

	DaysResp days;

	// Get Find Days
	public DaysResp findDays(Integer courseId) {

		// condition for error response
		if (Objects.isNull(courseId)) {
			return errorResponse();
		}

		try {
			courseEntity = courseRepository.getCourseEntityByCourseId(courseId);
		} catch (Exception e) {
			return errorResponse();
		}

		daysResponseList = new ArrayList<>();

		String startDate = courseEntity.getStartDate();
		String endDate = courseEntity.getEndDate();

		datesBetweenStartDateAndEndDate = date(startDate, endDate);

		// condition for error response
		if (Objects.isNull(courseEntity)) {
			return errorResponse();
		}

		int size = courseEntity.getDayEntity().size();

		int[] numCount = new int[size];

		int count = 0;

		// condition for error response
		if (Objects.isNull(courseEntity.getDayEntity())) {
			return errorResponse();
		}

		for (DayEntity dayEntityDays : courseEntity.getDayEntity()) {

			numCount[count] = dayEntityDays.getDayId();
			count = count + 1;
		}

		Arrays.sort(numCount);

		// daysResponseList = new ArrayList<>();

		for (int i = 0; i < size; i++) {
			daysResponse = successResponse(numCount, i);
			daysResponseList.add(daysResponse);
		}

		days = new DaysResp();
		days.setStatus(ApiConstants.SUCCESS);
		days.setMessage("Days");
		days.setDays(daysResponseList);

		return days;
	}

	private static List<LocalDate> getDatesBetweenStartDateAndEndDate(LocalDate startDate, LocalDate endDate) {

		long numOfDaysBetween = ChronoUnit.DAYS.between(startDate, endDate);
		return IntStream.iterate(0, i -> i + 1).limit(numOfDaysBetween).mapToObj(i -> startDate.plusDays(i))
				.collect(Collectors.toList());
	}

	private static List<LocalDate> date(String startDate, String endDate) {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");

		String sDate = convert(startDate);
		String eDate = convert(endDate);

		// convert String to LocalDate
		LocalDate localStartDate = LocalDate.parse(sDate, formatter);
		LocalDate localEndDate = LocalDate.parse(eDate, formatter).plusDays(1);

		List<LocalDate> datesBetweenStartDateAndEndDate = getDatesBetweenStartDateAndEndDate(localStartDate,
				localEndDate);

		return datesBetweenStartDateAndEndDate;

	}

	private static String convert(String startDate) {

		String m = "/";

		char d1 = startDate.charAt(8);
		char d2 = startDate.charAt(9);

		String dd = d1 + "" + d2;

		char m1 = startDate.charAt(5);
		char m2 = startDate.charAt(6);

		String mm = m1 + "" + m2;

		char y1 = startDate.charAt(0);
		char y2 = startDate.charAt(1);
		char y3 = startDate.charAt(2);
		char y4 = startDate.charAt(3);

		String yyyy = y1 + "" + y2 + "" + y3 + "" + y4;

		String date = dd + m + mm + m + yyyy;

		return date;

	}

	private DaysResponse successResponse(int[] numCount, int i) {

		daysResponse = new DaysResponse();

		daysResponse.setDayId(numCount[i]);
		try {
			dayEntity = dayRepository.getDayEntityByDayId(numCount[i]);
		} catch (Exception e) {
			// return errorResponse();
		}

		if (Objects.isNull(dayEntity)) {
			// return errorResponse();
		}

		daysResponse.setDate(datesBetweenStartDateAndEndDate.get(i));

		daysResponse.setDayName(dayEntity.getDayName());

		return daysResponse;
	}

	private DaysResp errorResponse() {

		days = new DaysResp();
		days.setStatus(ApiConstants.FAILURE);
		days.setMessage("Days");
		return days;
	}

}
