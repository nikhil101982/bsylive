package com.yoga.api.service;

import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yoga.api.entity.CourseEntity;
import com.yoga.api.model.AddCourseAdminRequest;
import com.yoga.api.model.StatusMessageResponse;
import com.yoga.api.repository.CourseRepository;
import com.yoga.api.util.UtilMethods;

@Service
public class AddCourseAdminService {

	@Autowired
	CourseRepository courseRepository;

	CourseEntity courseEntity;

	StatusMessageResponse statusMessageResponse = new StatusMessageResponse();

	UtilMethods utilMethods = new UtilMethods();

	String failureMessage;
	String successMessage;

	// Add Course api
	public StatusMessageResponse addCourse(AddCourseAdminRequest course) throws InterruptedException {

		failureMessage = "course is not created !";
		successMessage = " course have created successfully !";

		if (Objects.isNull(course)) {
			return utilMethods.errorResponse(failureMessage);
		}

		try {

			courseEntity = courseRepository.getCourseByCourseNameAndStartDateAndCouseDurationAndLanguage(
					course.getCourseName(), course.getStartDate(), course.getCouseDuration(), course.getLanguage());
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
			courseEntity.setCouseDuration(course.getCouseDuration());
			courseEntity.setLanguage(course.getLanguage());
			courseEntity.setStartDate(course.getStartDate());
			courseEntity.setEndDate(course.getEndDate());

			if (!Objects.isNull(courseEntity)) {
				courseRepository.save(courseEntity);
			}
			return utilMethods.successResponse(successMessage);
		} catch (Exception e) {

			return utilMethods.errorResponse(failureMessage);

		}
	}

}