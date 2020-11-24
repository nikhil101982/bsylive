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
import com.yoga.api.model.DayId;
import com.yoga.api.model.StatusMessageResponse;
import com.yoga.api.repository.CourseRepository;
import com.yoga.api.repository.DayRepository;
import com.yoga.api.repository.LectureRepository;
import com.yoga.api.util.UtilMethods;

@Service
public class TempService {

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

	StatusMessageResponse statusMessageResponse;

	LectureEntity lectureEntity;

	List<LectureEntity> lectureEntityList;

	UtilMethods utilMethods = new UtilMethods();

	final String failureMessage = "Lecture is not added !";
	final String successMessage = " Lecture is added successfully !";

	public StatusMessageResponse createLecture(CreateLectureTempRequest createLectureRequest) {

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

		if (courseEntity.getDayEntity().size() == 0) {
			dayEntityList = new ArrayList<>();
		} else {
			dayEntityList = courseEntity.getDayEntity();
		}

		for (DayId dayId : createLectureRequest.getDayIds()) {

			dayEntity = findDayEntity(dayId.getDayId());
			
			newDayEntity = new DayEntity();

			if (dayEntity.getLecEntity().size() == 0) {
				lectureEntityList = new ArrayList<>();
			} else {
				lectureEntityList = dayEntity.getLecEntity();
			}

			try {
				lectureEntity = lectureRepository.getLectureByLectureNameAndStartTimeAndEndTime(
						createLectureRequest.getLectureName(), createLectureRequest.getStartTime(),
						createLectureRequest.getEndTime());
			} catch (Exception e) {
				return utilMethods.errorResponse(failureMessage);
			}

			if (Objects.isNull(lectureEntity)) {
				lectureEntity = new LectureEntity();
				lectureEntity = createLectureList(createLectureRequest, lectureEntity);
				lectureEntityList.add(lectureEntity);
				saveLectureEntity(lectureEntityList);
			} else {
				lectureEntity = createLectureList(createLectureRequest, lectureEntity);
				lectureEntityList.add(lectureEntity);
				saveLectureEntity(lectureEntityList);

			}

			dayEntity.setLecEntity(lectureEntityList);
			dayEntityList.add(dayEntity);


		}

		saveAllDayRepository(dayEntityList);

		return saveCourseEntity(courseEntity, dayEntityList);

	}

	private StatusMessageResponse saveCourseEntity(CourseEntity courseEntity,
			List<DayEntity> dayEntityList) {

		try {
			courseEntity.setDayEntity(dayEntityList);
			courseRepository.save(courseEntity);
			return utilMethods.successResponse(successMessage);
		} catch (Exception e) {
			return utilMethods.errorResponse(failureMessage);

		}

	}
	
	private void saveAllDayRepository(List<DayEntity> dayEntityList) {
		try {
			dayRepository.saveAll(dayEntityList);
		} catch (Exception e) {
			utilMethods.errorResponse(failureMessage);
		}
	}

	private void saveDayRepository(DayEntity dayEntity) {
		try {
			dayRepository.save(dayEntity);
		} catch (Exception e) {
			utilMethods.errorResponse(failureMessage);
		}
	}

	private void saveLectureEntity(List<LectureEntity> lectureEntityList) {
		try {
			lectureRepository.saveAll(lectureEntityList);
		} catch (Exception e) {
			utilMethods.errorResponse(failureMessage);
		}
	}

	private void iframe(CreateLectureTempRequest LectByDay, LectureEntity lectureEntity) {

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

}