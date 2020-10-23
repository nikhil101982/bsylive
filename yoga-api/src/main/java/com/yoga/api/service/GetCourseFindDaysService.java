package com.yoga.api.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

		courseEntity = courseRepository.getCourseEntityByCourseId(courseId);

		daysResponseList = new ArrayList<>();

		int size = courseEntity.getDayEntity().size();

		int[] numCount = new int[size];
		
		int count =0;
		
		for (DayEntity dayEntityDays : courseEntity.getDayEntity()) {
			
			numCount[count] = dayEntityDays.getDayId();		
			count = count+1;
		}

		Arrays.sort(numCount);
		
		for(int i=0;i<size;i++) {
			
			daysResponse = new DaysResponse();

			daysResponse.setDayId(numCount[i]);
			dayEntity = dayRepository.getDayEntityByDayId(numCount[i]);
			daysResponse.setDayName(dayEntity.getDayName().toUpperCase());
			daysResponseList.add(daysResponse);
		}

		return daysResponseList;
	}

}
