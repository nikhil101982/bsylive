package com.yoga.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yoga.api.entity.CourseEntity;
import com.yoga.api.entity.DayEntity;
import com.yoga.api.entity.LectureEntity;
import com.yoga.api.model.CreateLectureRequest;
import com.yoga.api.model.DayByCourseId;
import com.yoga.api.model.DayId;
import com.yoga.api.model.LectureByDay;
import com.yoga.api.model.StatusMessageResponse;
import com.yoga.api.repository.CourseRepository;
import com.yoga.api.repository.DayRepository;
import com.yoga.api.repository.LectureRepository;
import com.yoga.api.util.UtilMethods;

@Service
public class LectureAdminService {

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

	final String failureMessage = "Lecture is not added !";
	final String successMessage = " Lecture is added successfully !";

	// create lecture
	public StatusMessageResponse createLecture(CreateLectureRequest createLectureRequest) throws InterruptedException {

		if (Objects.isNull(createLectureRequest)) {
			return utilMethods.errorResponse(failureMessage);
		}

		try {
			courseEntity = courseRepository.getCourseEntityByCourseId(createLectureRequest.getCourseId());
		} catch (Exception e) {
			return utilMethods.errorResponse(failureMessage);
		}

		if (Objects.isNull(courseEntity)) {
			return utilMethods.errorResponse(failureMessage);

		}

		// if (Objects.isNull(createLectureRequest) {
		// return utilMethods.errorResponse(failureMessage);
		// }

		dayEntityList = new ArrayList<>();
		lecEntityList = new ArrayList<>();

		// array of days

		for (DayId dayId : createLectureRequest.getDayIds()) {

			dayEntity = findDayEntity(dayId.getDayId());

			saveLectureEntity(createLectureRequest, dayEntity);

			saveRepository(dayEntity, lecEntityList);

		}

		dayEntity = findDayEntity(createLectureRequest.getFromDayId());

		int fromDay = Integer.parseInt(dayEntity.getDayName().substring(3));

		dayEntity = findDayEntity(createLectureRequest.getToDayId());

		int toDay = Integer.parseInt(dayEntity.getDayName().substring(3));

		for (int i = fromDay; i <= toDay; i++) {
			String dayName = "Day".concat(Integer.toString(i));
			findDayEntityByName(dayName);
		}

		return saveCourse(createLectureRequest, dayEntityList, courseEntity);

	}

	private void saveRepository(DayEntity dayEntity, List<LectureEntity> lecEntityList) {
		try {
			lectureRepository.saveAll(lecEntityList);
			dayEntity.setLecEntity(lecEntityList);
			dayRepository.save(dayEntity);
		} catch (Exception e) {
			utilMethods.errorResponse(failureMessage);
		}
	}

	private void saveLectureEntity(CreateLectureRequest createLectureRequest, DayEntity dayEntity) {

		if (dayEntity.getLecEntity().size() == 0) {
			lecEntityList.add(createLectureList(createLectureRequest));
		} else {
			for (LectureEntity lectureEntity : dayEntity.getLecEntity()) {

				lectureEntity = lectureRepository.getLecEntityByLecId(lectureEntity.getLectureId());

				if (!(lectureEntity.getLectureName().equals(createLectureRequest.getLectureName()))) {
					lecEntityList.add(createLectureList(createLectureRequest));
				}
			}
		}
	}

	private DayEntity findDayEntity(Integer dayId) {
		try {
			dayEntity = dayRepository.getDayEntityByDayId(dayId);
		} catch (Exception e) {
			utilMethods.errorResponse(failureMessage);
		}
		return dayEntity;
	}

	private void findDayEntityByName(String dayName) {
		try {
			dayEntity = dayRepository.getDayEntityByDayName(dayName);
		} catch (Exception e) {
			utilMethods.errorResponse(failureMessage);
		}

	}

	private StatusMessageResponse saveCourse(CreateLectureRequest createLectureRequest, List<DayEntity> dayEntityList,
			CourseEntity courseEntity) {

		try {
			courseEntity = new CourseEntity();
			courseEntity.setCouseDuration(dayEntityList.size());
			courseEntity.setDayEntity(dayEntityList);
			courseEntity.setCourseId(createLectureRequest.getCourseId());
			courseRepository.save(courseEntity);
			return utilMethods.successResponse(successMessage);
		} catch (Exception e) {
			return utilMethods.errorResponse(failureMessage);

		}

	}

	private LectureEntity createLectureList(CreateLectureRequest createLectureRequest) {

		if (!Objects.isNull(createLectureRequest)) {

			iframe(createLectureRequest);

			lectureEntity.setCurrDate(createLectureRequest.getCurrentDate().toUpperCase());
			lectureEntity.setEndTime(createLectureRequest.getEndTime().toUpperCase());
			lectureEntity.setLectureName(createLectureRequest.getLectureName());
			lectureEntity.setStartTime(createLectureRequest.getStartTime().toUpperCase());
		}
		return lectureEntity;
	}

	private void iframe(CreateLectureRequest LectByDay) {

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