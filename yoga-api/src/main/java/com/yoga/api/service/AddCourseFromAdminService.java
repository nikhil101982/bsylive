package com.yoga.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yoga.api.entity.CourseEntity;
import com.yoga.api.entity.DayEntity;
import com.yoga.api.entity.LectureEntity;
import com.yoga.api.model.AddCourseByDayId;
import com.yoga.api.model.DayByCourseId;
import com.yoga.api.model.LectureByDay;
import com.yoga.api.model.StatusMessageResponse;
import com.yoga.api.repository.CourseRepository;
import com.yoga.api.repository.DayRepository;
import com.yoga.api.repository.LectureRepository;
import com.yoga.api.util.UtilMethods;

@Service
public class AddCourseFromAdminService {

	@Autowired
	CourseRepository courseRepository;

	@Autowired
	DayRepository dayRepository;

	@Autowired
	LectureRepository lectureRepository;

	CourseEntity courseEntity;

	StatusMessageResponse statusMessageResponse = new StatusMessageResponse();

	DayByCourseId dayByCourseId;
	DayEntity dayEntity;
	List<DayEntity> dayEntityList;

	LectureByDay LectureByDay;
	LectureEntity lectureEntity;
	List<LectureEntity> lecEntityList;

	List<List<LectureEntity>> lecEntityListOfList;

	UtilMethods utilMethods = new UtilMethods();

	// Add Course api
	public StatusMessageResponse addCourse(AddCourseByDayId course) throws InterruptedException {

		final String failureMessage = "course is not created !";
		final String successMessage = " course have created successfully !";

		// condition for error response
		if (Objects.isNull(course)) {

			return utilMethods.errorResponse(failureMessage);
		}

		try {
			courseEntity = courseRepository.getCourseByCourseNameAndStartDateAndCouseDuration(course.getCourseName(),
					course.getStartDate(), course.getDay().size());
		} catch (Exception e) {

			return utilMethods.errorResponse(failureMessage);

		}

		if (!Objects.isNull(courseEntity)) {
			return utilMethods.errorResponse(failureMessage);

		}

		if (Objects.isNull(course.getDay())) {

			return utilMethods.errorResponse(failureMessage);

		}

		lecEntityListOfList = new ArrayList<>();
		dayEntityList = new ArrayList<>();

		for (DayByCourseId day : course.getDay()) {

			lecEntityList = new ArrayList<>();

			for (LectureByDay LectByDay : day.getLecture()) {
				lectureEntity = new LectureEntity();
				createLectureList(LectByDay);
			}

			dayEntity = new DayEntity();

			if (!Objects.isNull(lecEntityList)) {
				lectureRepository.saveAll(lecEntityList);
				dayEntity.setLecEntity(lecEntityList);

			}

			dayEntity.setDayName(day.getDayName());
			dayEntityList.add(dayEntity);
		}

		if (!Objects.isNull(dayEntityList)) {
			dayRepository.saveAll(dayEntityList);
		}

		saveCourse(course);

		return utilMethods.successResponse(successMessage);

	}

	private void saveCourse(AddCourseByDayId course) {
		try {
			courseEntity = new CourseEntity();
			courseEntity.setCourseName(course.getCourseName());
			courseEntity.setCouseDuration(dayEntityList.size());

			courseEntity.setDayEntity(dayEntityList);
			courseEntity.setStartDate(course.getStartDate());

			if (!Objects.isNull(courseEntity)) {
				courseRepository.save(courseEntity);
			}
		} catch (Exception e) {

		}
	}

	private void createLectureList(LectureByDay LectByDay) {
		if (!Objects.isNull(LectByDay)) {

			// set video and live iframe link
			iframe(LectByDay);

			lectureEntity.setSNo(LectByDay.getSNo());
			lectureEntity.setCurrDate(LectByDay.getCurrentDate().toUpperCase());
			lectureEntity.setEndTime(LectByDay.getEndTime().toUpperCase());
			lectureEntity.setLectureName(LectByDay.getLectureName());
			lectureEntity.setStartTime(LectByDay.getStartTime().toUpperCase());
			lecEntityList.add(lectureEntity);
			lecEntityListOfList.add(lecEntityList);

		}
	}

	private void iframe(LectureByDay LectByDay) {

		if (!Objects.isNull(lectureEntity.getVideoIframeDynamicLink())) {
			lectureEntity.setVideoIframeDynamicLink(LectByDay.getVideoIframeDynamicLink());
			lectureEntity.setDisableJoinBtn("false");
			lectureEntity.setLiveIframeDynamicLink(null);

		} else {
			lectureEntity.setVideoIframeDynamicLink(null);
			lectureEntity.setDisableJoinBtn("false");
			lectureEntity.setLiveIframeDynamicLink(LectByDay.getLiveIframeDynamicLink());

		}

	}

}