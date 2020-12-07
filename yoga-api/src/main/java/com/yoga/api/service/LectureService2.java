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
public class LectureService2 {

	@Autowired
	CourseRepository courseRepository;

	@Autowired
	DayRepository dayRepository;

	@Autowired
	LectureRepository lectureRepository;

	CourseEntity courseEntity;

	DayEntity dayEntity;

	DayEntity newDayEntity;

	StatusMessageResponse statusMessageResponse = new StatusMessageResponse();

	LectureEntity lectureEntity;

	List<LectureEntity> lectureEntityList;
	List<DayEntity> dayEntityList;

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

		dayEntityList = courseEntity.getDayEntity();

		for (DayId dayId : createLectureRequest.getDayIds()) {

			dayEntity = findDayEntity(dayId.getDayId());
			lectureEntityList = dayEntity.getLecEntity();
			int numberOfLectureInDay = dayEntity.getLecEntity().size();

			try {

				lectureEntity = lectureRepository.getLectureByLectureNameAndStartTimeAndEndTime(
						createLectureRequest.getLectureName(), createLectureRequest.getStartTime(),
						createLectureRequest.getEndTime());
			} catch (Exception e) {
			}

			if (numberOfLectureInDay == 0) {

				if (Objects.isNull(lectureEntity)) {
					LectureEntity lectureEntityNew = new LectureEntity();
					lectureEntityNew = addLecture(createLectureRequest, lectureEntityNew);
					lectureEntityList.add(0, lectureEntityNew);

				} else {

					LectureEntity lectureEntityNew = new LectureEntity();
					lectureEntityNew = addLecture(createLectureRequest, lectureEntity);
					lectureEntityList.add(0, lectureEntityNew);

				}

			}

			else if (numberOfLectureInDay > 0) {

				if (Objects.isNull(lectureEntity)) {

					LectureEntity lectureEntityNew = new LectureEntity();
					lectureEntityNew = addLecture(createLectureRequest, lectureEntityNew);
					int index = lectureEntityList.indexOf(lectureEntityNew);

					if (index == -1) {
						lectureEntityList.add(lectureEntityNew);
					} else {
						lectureEntityList.add(index, lectureEntityNew);

					}
				} else {

					LectureEntity lectureEntityNew = new LectureEntity();
					lectureEntityNew = addLecture(createLectureRequest, lectureEntity);

					int index = lectureEntityList.indexOf(lectureEntity);

					if (index == -1) {
						lectureEntityList.add(lectureEntityNew);
					} else {
						lectureEntityList.add(index, lectureEntityNew);

					}
				}

			}

			saveAllLectureEntity(lectureEntityList);

			dayEntity.setLecEntity(lectureEntityList);

			int dayEntityIndex = dayEntityList.indexOf(dayEntity);
			dayEntityList.add(dayEntityIndex, dayEntity);
		}

		if (!Objects.isNull(dayEntityList)) {
			saveAllDayRepository(dayEntityList);
		}

		if (!Objects.isNull(courseEntity)) {

			courseEntity.setDayEntity(dayEntityList);

			// courseRepository.updateCourseEntity(dayEntityList,
			// courseEntity.getCourseId());
			courseRepository.save(courseEntity);

			return utilMethods.successResponse(successMessage);

		} else {
			return utilMethods.errorResponse(failureMessage);

		}

	}

	private LectureEntity addLecture(CreateLectureTempRequest createLectureRequest, LectureEntity lectureEntity) {

		lectureEntity = createLectureList(createLectureRequest, lectureEntity);
		return lectureEntity;

	}

	private void saveCourseEntity(CourseEntity courseEntity, List<DayEntity> dayEntityList) {

		try {
			courseEntity.setDayEntity(dayEntityList);
			courseRepository.save(courseEntity);
		} catch (Exception e) {

		}

	}

	private void saveDayRepository(DayEntity dayEntityList) {
		try {
			dayRepository.save(dayEntityList);
		} catch (Exception e) {
			utilMethods.errorResponse(failureMessage);
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

	private void saveLectureEntity(LectureEntity lectureEntity) {
		try {

			lectureRepository.save(lectureEntity);
			// lectureRepository.updateLectureEntity(lectureEntity.getLectureName(),
			// lectureEntity.startTime, lectureEntity.getEndTime(),
			// lectureEntity.getCurrDate(), lectureEntity.getVideoIframeDynamicLink(),
			// lectureEntity.getLiveIframeDynamicLink(), lectureEntity.getDisableJoinBtn(),
			// lectureEntity.getLectureId());
		} catch (Exception e) {
			utilMethods.errorResponse(failureMessage);
		}
	}

	private void iframe(CreateLectureTempRequest LectByDay, LectureEntity lectureEntity) {

		boolean checkVideoIframeDynamicLink = false;
		boolean checkLiveIframeDynamicLink = false;

		if (Objects.isNull(LectByDay.getLiveIFrameLink())) {
			checkVideoIframeDynamicLink = LectByDay.getVideoIFrameLink().isEmpty();

		} else {
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
			// dayEntity = new DayEntity();
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