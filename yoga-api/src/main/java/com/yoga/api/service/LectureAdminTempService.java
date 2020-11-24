package com.yoga.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yoga.api.entity.CourseEntity;
import com.yoga.api.entity.DayEntity;
import com.yoga.api.entity.LectureEntity;
import com.yoga.api.model.CreateLectureTempRequest;
import com.yoga.api.model.DayByCourseId;
import com.yoga.api.model.DayId;
import com.yoga.api.model.LectureByDay;
import com.yoga.api.model.StatusMessageResponse;
import com.yoga.api.repository.CourseRepository;
import com.yoga.api.repository.DayRepository;
import com.yoga.api.repository.LectureRepository;
import com.yoga.api.util.UtilMethods;

@Service
public class LectureAdminTempService {

	@Autowired
	CourseRepository courseRepository;

	@Autowired
	DayRepository dayRepository;

	@Autowired
	LectureRepository lectureRepository;

	CourseEntity courseEntity;

	StatusMessageResponse statusMessageResponse = new StatusMessageResponse();

	DayByCourseId dayByCourseId = new DayByCourseId();
	DayEntity dayEntity;
	List<DayEntity> dayEntityList = new ArrayList<>();

	LectureByDay LectureByDay = new LectureByDay();
	LectureEntity lectureEntity;
	List<LectureEntity> lecEntityList  = new ArrayList<>();

	List<List<LectureEntity>> lecEntityListOfList  = new ArrayList<>();

	UtilMethods utilMethods = new UtilMethods();

	final String failureMessage = "Lecture is not added !";
	final String successMessage = " Lecture is added successfully !";

	// create lecture
	public StatusMessageResponse createLecture(CreateLectureTempRequest createLectureRequest)
			throws InterruptedException {

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

		// array of days

		for (DayId dayId : createLectureRequest.getDayIds()) {

			dayEntity = findDayEntity(dayId.getDayId());

			if (dayEntity.getLecEntity().size() == 0) {
				
				lectureEntity = new LectureEntity();
				
				lecEntityList.add(createLectureList(createLectureRequest,lectureEntity ));

				try {
					lectureRepository.saveAll(lecEntityList);
				} catch (Exception e) {
					utilMethods.errorResponse(failureMessage);
				}

			} else {
				for (LectureEntity lectureEntity : dayEntity.getLecEntity()) {

					lectureEntity = lectureRepository.getLecEntityByLecId(lectureEntity.getLectureId());

					if (!(lectureEntity.getLectureName().equals(createLectureRequest.getLectureName()))) {
						lecEntityList.add(createLectureList(createLectureRequest , lectureEntity));
					}
				}

				try {
					lectureRepository.saveAll(lecEntityList);
				} catch (Exception e) {
					utilMethods.errorResponse(failureMessage);
				}

			}

			dayEntityList.add(dayEntity);

		}

		try {
			dayRepository.saveAll(dayEntityList);
		} catch (Exception e) {
			utilMethods.errorResponse(failureMessage);
		}

		try {
			//courseEntity = new CourseEntity();
			courseEntity.setCouseDuration(dayEntityList.size());
			courseEntity.setDayEntity(dayEntityList);
			courseEntity.setCourseId(createLectureRequest.getCourseId());
			courseRepository.save(courseEntity);
			return utilMethods.successResponse(successMessage);
		} catch (Exception e) {
			return utilMethods.errorResponse(failureMessage);

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

	private LectureEntity createLectureList(CreateLectureTempRequest createLectureRequest, LectureEntity lectureEntity) {

		if (!Objects.isNull(createLectureRequest)) {

			iframe(createLectureRequest , lectureEntity);

			// lectureEntity.setCurrDate(createLectureRequest.getCurrentDate().toUpperCase());
			lectureEntity.setEndTime(createLectureRequest.getEndTime().toUpperCase());
			lectureEntity.setLectureName(createLectureRequest.getLectureName().toUpperCase());
			lectureEntity.setStartTime(createLectureRequest.getStartTime().toUpperCase());
		}
		return lectureEntity;
	}

	private void iframe(CreateLectureTempRequest LectByDay, LectureEntity lectureEntity2) {
		
		boolean checkVideoIframeDynamicLink = LectByDay.getVideoIFrameLink().isEmpty();
		boolean checkLiveIframeDynamicLink = LectByDay.getLiveIFrameLink().isEmpty();


		if (checkVideoIframeDynamicLink && checkLiveIframeDynamicLink) 
		
		{
			lectureEntity.setDisableJoinBtn("true");

		} else if (!checkVideoIframeDynamicLink && !checkLiveIframeDynamicLink) {

			lectureEntity.setDisableJoinBtn("true");
			lectureEntity.setLiveIframeDynamicLink(LectByDay.getLiveIFrameLink());
			lectureEntity.setVideoIframeDynamicLink(LectByDay.getVideoIFrameLink());

		} else {

			if (!checkVideoIframeDynamicLink) {
				
				lectureEntity.setVideoIframeDynamicLink(LectByDay.getVideoIFrameLink());
				lectureEntity.setDisableJoinBtn("false");

			} 
			else if(!checkLiveIframeDynamicLink) {
				lectureEntity.setDisableJoinBtn("false");
				lectureEntity.setLiveIframeDynamicLink(LectByDay.getLiveIFrameLink());

			}

		}

	}

}