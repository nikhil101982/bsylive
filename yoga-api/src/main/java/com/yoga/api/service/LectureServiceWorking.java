package com.yoga.api.service;

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

// create lecture api

// new lecture : 

@Service
public class LectureServiceWorking {

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

			try {

				lectureEntity = lectureRepository.getLectureByLectureNameAndStartTimeAndEndTime(
						createLectureRequest.getLectureName(), createLectureRequest.getStartTime(),
						createLectureRequest.getEndTime());
			} catch (Exception e) {
			}

			if (dayEntity.getLecEntity().size() != 0) {

				// for (LectureEntity lectureEntity3 : dayEntity.getLecEntity()) {

				// if (lectureEntity3.equals(lectureEntity)) {

				// if (lectureEntity3.getLectureStatus().equals(ApiConstants.TRUE)) {

				LectureEntity newlectureEntity = addLecture(createLectureRequest, lectureEntity);

				int index = lectureEntityList.indexOf(newlectureEntity);

				// lectureEntityList.remove(index);

				lectureEntityList.add(index, newlectureEntity);

				return utilMethods.successResponse(successMessage);

				// }

				// }

				// }
			} else {

				if (Objects.isNull(lectureEntity)) {

					lectureEntity = new LectureEntity();
					lectureEntity = addLecture(createLectureRequest, lectureEntity);
					// lectureEntity.setLectureStatus(ApiConstants.TRUE);
					lectureEntityList.add(0, lectureEntity);
					saveLectureEntity(lectureEntity);

				} else {
					lectureEntity = addLecture(createLectureRequest, lectureEntity);
					
					int index = lectureEntityList.indexOf(lectureEntity);

					// lectureEntity.setLectureStatus(ApiConstants.TRUE);

					if (index == -1) {
						lectureEntityList.add(0, lectureEntity);

					} else {
						lectureEntityList.add(index, lectureEntity);

					}

					// lectureEntityList.add(lectureEntity);
					saveLectureEntity(lectureEntity);

				}
			}

			dayEntity.setLecEntity(lectureEntityList);
			int dayEntityIndex = dayEntityList.indexOf(dayEntity);
			// dayEntityList.remove(dayEntityIndex);
			dayEntityList.add(dayEntityIndex, dayEntity);

		}

		if (!Objects.isNull(dayEntityList)) {
			saveAllDayRepository(dayEntityList);
		}

		if (!Objects.isNull(courseEntity)) {

			courseEntity.setDayEntity(dayEntityList);
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

	private void saveAllDayRepository(List<DayEntity> dayEntityList) {
		try {
			dayRepository.saveAll(dayEntityList);
		} catch (Exception e) {
			utilMethods.errorResponse(failureMessage);
		}
	}

	private void saveLectureEntity(LectureEntity lectureEntity) {
		try {
			lectureRepository.save(lectureEntity);
		} catch (Exception e) {
			utilMethods.errorResponse(failureMessage);
		}
	}

	private void iframe(CreateLectureTempRequest LectByDay, LectureEntity lectureEntity) {

		boolean checkVideoIframeDynamicLink = false;
		boolean checkLiveIframeDynamicLink = false;

		if (!Objects.isNull(LectByDay.getLiveIFrameLink())) {
			checkLiveIframeDynamicLink = LectByDay.getLiveIFrameLink().isEmpty();

		} else if (!Objects.isNull(LectByDay.getVideoIFrameLink())) {
			checkVideoIframeDynamicLink = LectByDay.getVideoIFrameLink().isEmpty();

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