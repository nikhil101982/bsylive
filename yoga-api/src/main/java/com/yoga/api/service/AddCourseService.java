package com.yoga.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yoga.api.entity.CourseEntity;
import com.yoga.api.entity.LecEntity;
import com.yoga.api.model.AddCourseResponse;
import com.yoga.api.model.CourseResources;
import com.yoga.api.model.Day;
import com.yoga.api.repository.CourseRepository;
import com.yoga.api.repository.LecRepository;

@Service
public class AddCourseService {

	@Autowired
	CourseRepository courseRepository;

	@Autowired
	LecRepository lectureRepository;

	CourseEntity courseEntity;

	LecEntity lectureEntity = new LecEntity();

	AddCourseResponse addCourseResponse;

	List<LecEntity> lecEntityList = new ArrayList<>();

	// Add Course api
	public AddCourseResponse addCourse(CourseResources course) {

		String status = null;

		courseEntity = courseRepository.getCourseByCourseNameAndStartDateAndCouseDuration(course.getCourseName(),
				course.getStartDate(), course.getCouseDuration());

		addCourseResponse = new AddCourseResponse();

		if (Objects.isNull(courseEntity)) {

			addLectures(course);

			createCourse(course);

			status = "Successfully added the course";

			addCourseResponse(course, status);

			return addCourseResponse;

		} else {

			status = "Not able to add the course";

			addCourseResponse(course, status);

			return addCourseResponse;

		}

	}

	private void createCourse(CourseResources course) {
		courseEntity = new CourseEntity();
		courseEntity.setCourseName(course.getCourseName().toUpperCase());
		courseEntity.setCouseDuration(course.getCouseDuration().toUpperCase());
		courseEntity.setStartDate(course.getStartDate().toUpperCase());

		courseRepository.save(courseEntity);
	}

	private void addLectures(CourseResources course) {
		for (Day day : course.getDay()) {

			lectureEntity.setCurrDate(day.getCurrentDate().toUpperCase());
			lectureEntity.setDisableJoinBtn(false);
			lectureEntity.setEndTime(day.getEndTime().toUpperCase());
			lectureEntity.setLectureName(day.getLectureName().toUpperCase());
			lectureEntity.setStartTime(day.getStartTime().toUpperCase());
			lectureEntity.setIframeDynamicLink(day.getIframeDynamicLink());

			lecEntityList.add(lectureEntity);
		}

		if (!Objects.isNull(lecEntityList)) {
			lectureRepository.saveAll(lecEntityList);
		}
	}

	private void addCourseResponse(CourseResources course, String status) {
		addCourseResponse.setCourseName(course.getCourseName());
		addCourseResponse.setCouseDuration(course.getCouseDuration());
		addCourseResponse.setMessage(status);
		addCourseResponse.setStartDate(course.getStartDate());
	}

}
