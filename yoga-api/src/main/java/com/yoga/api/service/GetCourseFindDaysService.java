package com.yoga.api.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yoga.api.constant.ApiConstants;
import com.yoga.api.entity.CourseEntity;
import com.yoga.api.entity.DayEntity;
import com.yoga.api.model.DaysResponse;
import com.yoga.api.repository.CourseRepository;
import com.yoga.api.repository.DayRepository;

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

	// Get Find Days
	public List<DaysResponse> findDays(Integer courseId) {

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
		daysResponse = new DaysResponse();

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

		for (int i = 0; i < size; i++) {
			successResponse(numCount, i);
		}

		return daysResponseList;
	}

	private List<DaysResponse> successResponse(int[] numCount, int i) {

		daysResponse = new DaysResponse();

		daysResponse.setDayId(numCount[i]);
		try {
			dayEntity = dayRepository.getDayEntityByDayId(numCount[i]);
		} catch (Exception e) {
			return errorResponse();
		}

		if (Objects.isNull(dayEntity)) {
			return errorResponse();
		}
		daysResponse.setDayName(dayEntity.getDayName().toUpperCase());
		daysResponseList.add(daysResponse);
		return daysResponseList;
	}

	private List<DaysResponse> errorResponse() {
		daysResponseList.add(daysResponse);
		return daysResponseList;
	}

}
