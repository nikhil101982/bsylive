package com.yoga.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yoga.api.constant.ApiConstants;
import com.yoga.api.entity.CourseEntity;
import com.yoga.api.entity.DayEntity;
import com.yoga.api.entity.LectureEntity;
import com.yoga.api.model.CreateLectureTempRequest;
import com.yoga.api.model.DayId;
import com.yoga.api.model.StatusMessageResponse;
import com.yoga.api.repository.CourseRepository;
import com.yoga.api.repository.DayRepository;
import com.yoga.api.repository.LectureRepository;
import com.yoga.api.util.UtilMethods;

@Service
public class LectureService {

	@Autowired
	CourseRepository courseRepository;

	@Autowired
	DayRepository dayRepository;

	@Autowired
	LectureRepository lectureRepository;

	CourseEntity courseEntity;

	DayEntity dayEntity;

	DayEntity newDayEntity;

	List<DayEntity> dayEntityList;

	StatusMessageResponse statusMessageResponse = new StatusMessageResponse();

	LectureEntity lectureEntity;

	List<LectureEntity> lectureEntityList;

	UtilMethods utilMethods = new UtilMethods();

	final String failureMessage = "Lecture is not added !";
	final String successMessage = " Lecture is added successfully !";

	public StatusMessageResponse createLecture(CreateLectureTempRequest createLectureRequest) {

		if (Objects.isNull(createLectureRequest)) {

			errorResponse(failureMessage);
		}

		try {
			courseEntity = courseRepository.getCourseEntityByCourseId(createLectureRequest.getCourseId());
		} catch (Exception e) {
			errorResponse(failureMessage);
		}

		if (Objects.isNull(courseEntity)) {
			errorResponse(failureMessage);

		}

		if (courseEntity.getDayEntity().size() == 0) {
			dayEntityList = new ArrayList<>();
		} else {
			dayEntityList = courseEntity.getDayEntity();
		}

		for (DayId dayId : createLectureRequest.getDayIds()) {

			dayEntity = findDayEntity(dayId.getDayId());
			lectureEntity = new LectureEntity();

			try {
				lectureEntity = lectureRepository.getLectureByLectureNameAndStartTimeAndEndTime(
						createLectureRequest.getLectureName(), createLectureRequest.getStartTime(),
						createLectureRequest.getEndTime());
			} catch (Exception e) {

				errorResponse(failureMessage);

			}

			if (dayEntity.getLecEntity().size() == 0) {

				lectureEntityList = new ArrayList<>();

				addLecture(createLectureRequest);

			} else {

				lectureEntityList = dayEntity.getLecEntity();

				addLecture(createLectureRequest);

			}

			if (!Objects.isNull(lectureEntityList)) {
				dayEntity.setLecEntity(lectureEntityList);
			}

			if (!Objects.isNull(dayEntity)) {
				dayEntityList.add(dayEntity);
			}

		}

		if (!Objects.isNull(dayEntityList)) {
			saveAllDayRepository(dayEntityList);
		}

		if (!Objects.isNull(courseEntity)) {
			saveCourseEntity(courseEntity, dayEntityList);

		}

		return statusMessageResponse;

	}

	private void addLecture(CreateLectureTempRequest createLectureRequest) {
		if (Objects.isNull(lectureEntity)) {
			LectureEntity lecEntity = new LectureEntity();
			lecEntity = createLectureList(createLectureRequest, lecEntity);
			lectureEntityList.add(lecEntity);
			saveAllLectureEntity(lectureEntityList);
		} else {
			lectureEntityList.add(lectureEntity);
		}
	}

	private void saveCourseEntity(CourseEntity courseEntity, List<DayEntity> dayEntityList) {

		try {
			courseEntity.setDayEntity(dayEntityList);
			courseRepository.save(courseEntity);
		} catch (Exception e) {

		}

	}

	private void saveAllDayRepository(List<DayEntity> dayEntityList) {
		try {
			dayRepository.saveAll(dayEntityList);
		} catch (Exception e) {
			utilMethods.errorResponse(failureMessage);
		}
	}

	private void saveAllLectureEntity(List<LectureEntity> lectureEntityList) {
		try {
			lectureRepository.saveAll(lectureEntityList);
		} catch (Exception e) {
			utilMethods.errorResponse(failureMessage);
		}
	}

	private void iframe(CreateLectureTempRequest LectByDay, LectureEntity lectureEntity) {
		
		boolean checkVideoIframeDynamicLink = false;
		boolean checkLiveIframeDynamicLink = false;

		if(Objects.isNull(LectByDay.getLiveIFrameLink())) {
			 checkVideoIframeDynamicLink = LectByDay.getVideoIFrameLink().isEmpty();

		}else {
			 checkLiveIframeDynamicLink = LectByDay.getLiveIFrameLink().isEmpty();

		}

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

			} else if (!checkLiveIframeDynamicLink) {
				lectureEntity.setDisableJoinBtn("false");
				lectureEntity.setLiveIframeDynamicLink(LectByDay.getLiveIFrameLink());

			}

		}

	}

	private LectureEntity createLectureList(CreateLectureTempRequest createLectureRequest,
			LectureEntity lectureEntity) {

		if (!Objects.isNull(createLectureRequest)) {
			iframe(createLectureRequest, lectureEntity);
			lectureEntity.setEndTime(createLectureRequest.getEndTime().toUpperCase());
			lectureEntity.setLectureName(createLectureRequest.getLectureName().toUpperCase());
			lectureEntity.setStartTime(createLectureRequest.getStartTime().toUpperCase());
		}
		return lectureEntity;
	}

	private DayEntity findDayEntity(Integer dayId) {
		try {
			dayEntity = new DayEntity();
			dayEntity = dayRepository.getDayEntityByDayId(dayId);
		} catch (Exception e) {
			utilMethods.errorResponse(failureMessage);
		}
		return dayEntity;
	}

	public void errorResponse(String message) {
		statusMessageResponse.setMessage(message);
		statusMessageResponse.setStatus(ApiConstants.FAILURE);
	}

	public void successResponse(String message) {
		statusMessageResponse.setMessage(message);
		statusMessageResponse.setStatus(ApiConstants.SUCCESS);

	}

}