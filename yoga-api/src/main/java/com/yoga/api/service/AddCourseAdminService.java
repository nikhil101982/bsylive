package com.yoga.api.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yoga.api.entity.CourseEntity;
import com.yoga.api.entity.DayEntity;
import com.yoga.api.model.AddCourseAdminRequest;
import com.yoga.api.model.StatusMessageResponse;
import com.yoga.api.repository.CourseRepository;
import com.yoga.api.repository.DayRepository;
import com.yoga.api.util.CompareDates;
import com.yoga.api.util.UtilMethods;

@Service
public class AddCourseAdminService {

	@Autowired
	CourseRepository courseRepository;

	@Autowired
	DayRepository dayRepository;

	CourseEntity courseEntity;

	List<DayEntity> dayEntityList;

	DayEntity dayEntity;

	StatusMessageResponse statusMessageResponse = new StatusMessageResponse();

	UtilMethods utilMethods = new UtilMethods();

	CompareDates compareDates = new CompareDates();

	String failureMessage;
	String successMessage;

	// Add Course api
	public StatusMessageResponse addCourse(AddCourseAdminRequest course) throws InterruptedException, ParseException {

		failureMessage = "course is not created !";
		successMessage = " course have created successfully !";

		if (Objects.isNull(course)) {
			return utilMethods.errorResponse(failureMessage);
		}

		int duration = CompareDates.findDifference(course.getStartDate(), course.getEndDate());

		course.setDuration(duration);

		try {

			courseEntity = courseRepository.getCourseByCourseNameAndStartDateAndCouseDurationAndLanguage(
					course.getCourseName(), course.getStartDate(), course.getDuration(), course.getLanguage());
		} catch (Exception e) {
			return utilMethods.errorResponse(failureMessage);
		}

		if (!Objects.isNull(courseEntity)) {
			return utilMethods.errorResponse(failureMessage);

		}

		return saveCourse(course);

	}

	private StatusMessageResponse saveCourse(AddCourseAdminRequest course) {
		try {
			courseEntity = new CourseEntity();
			courseEntity.setCourseName(course.getCourseName());
			courseEntity.setCouseDuration(course.getDuration());
			courseEntity.setLanguage(course.getLanguage().toUpperCase());
			courseEntity.setStartDate(course.getStartDate());
			courseEntity.setEndDate(course.getEndDate());
			courseEntity.setDayEntity(createDayEntityList(course));

			if (!Objects.isNull(courseEntity)) {
				courseRepository.save(courseEntity);
			}
			return utilMethods.successResponse(successMessage);
		} catch (Exception e) {

			return utilMethods.errorResponse(failureMessage);

		}
	}

	private List<DayEntity> createDayEntityList(AddCourseAdminRequest course) {

		dayEntityList = new ArrayList<>();

		for (int i = 1; i <= course.getDuration(); i++) {

			dayEntity = new DayEntity();

			String dayName = "Day".concat(Integer.toString(i));

			dayEntity.setDayName(dayName);
			dayEntityList.add(dayEntity);
		}

		if (!Objects.isNull(dayEntityList)) {
			dayRepository.saveAll(dayEntityList);
		}

		return dayEntityList;
	}

}