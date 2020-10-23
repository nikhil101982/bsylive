package com.yoga.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yoga.api.entity.CourseEntity;
import com.yoga.api.entity.LectureEntity;
import com.yoga.api.model.AddCourseResponse;
import com.yoga.api.model.CourseResources;
import com.yoga.api.model.Day;
import com.yoga.api.repository.CourseRepository;
import com.yoga.api.repository.LectureRepository;

@Service
public class AddCourseService {

	@Autowired
	CourseRepository courseRepository;

	@Autowired
	LectureRepository lectureRepository;

	CourseEntity courseEntity;

	LectureEntity lectureEntity = new LectureEntity();

	AddCourseResponse addCourseResponse;

	List<LectureEntity> lecEntityList = new ArrayList<>();

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
			lectureEntity.setVideoIframeDynamicLink(day.getVideoIframeDynamicLink());
			lectureEntity.setLiveIframeDynamicLink(day.getLiveIframeDynamicLink());

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
