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

		addCourseResponse = new AddCourseResponse();

		String status = null;

		if (Objects.isNull(course)) {
			return errorResponse();
		}

		try {
			courseEntity = courseRepository.getCourseByCourseNameAndStartDateAndCouseDuration(course.getCourseName(),
					course.getStartDate(), course.getDay().size());
		} catch (Exception e) {
			return errorResponse();
		}

		if (!Objects.isNull(courseEntity)) {
			return errorResponse();

		}

		try {
			addLectures(course);
		} catch (Exception e) {
			return errorResponse();
		}

		try {
			createCourse(course);
		} catch (Exception e) {
			return errorResponse();
		}

		return addCourseResponse(course, status);

	}

	private AddCourseResponse errorResponse() {
		addCourseResponse.setMessage("course not added !");
		addCourseResponse.setStatus("failure");
		return addCourseResponse;
	}

	private void createCourse(CourseResources course) {
		courseEntity = new CourseEntity();
		courseEntity.setCourseName(course.getCourseName().toUpperCase());
		courseEntity.setCouseDuration(course.getDay().size());
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

	private AddCourseResponse addCourseResponse(CourseResources course, String status) {
		addCourseResponse.setCourseName(course.getCourseName());
		addCourseResponse.setCouseDuration(course.getDay().size());
		addCourseResponse.setMessage("course added !");
		addCourseResponse.setStartDate(course.getStartDate());
		addCourseResponse.setStatus("success");
		return addCourseResponse;
	}

}
