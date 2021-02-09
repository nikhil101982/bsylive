package com.yoga.api.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yoga.api.constant.ApiConstants;
import com.yoga.api.entity.CourseEntity;
import com.yoga.api.entity.DayEntity;
import com.yoga.api.entity.LectureEntity;
import com.yoga.api.model.CreateLectureTempRequest;
import com.yoga.api.model.DayId;
import com.yoga.api.model.DaysInLectures;
import com.yoga.api.model.DaysResponse;
import com.yoga.api.model.LectureByDay;
import com.yoga.api.model.LectureEntityStatus;
import com.yoga.api.model.LectureListInCourse;
import com.yoga.api.model.LectureReference;
import com.yoga.api.model.StatusMessageResponse;
import com.yoga.api.repository.CourseRepository;
import com.yoga.api.repository.DayRepository;
import com.yoga.api.repository.LectureRepository;
import com.yoga.api.util.UtilMethods;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class LectureService {

	@Autowired
	CourseRepository courseRepository;

	@Autowired
	DayRepository dayRepository;

	@Autowired
	LectureRepository lectureRepository;

	CourseEntity courseEntity;

	List<CourseEntity> courseEntityList;

	DayEntity dayEntity;

	StatusMessageResponse statusMessageResponse = new StatusMessageResponse();

	LectureListInCourse lectureListInCourse;

	LectureEntity lectureEntity;

	List<LectureEntity> lectureEntityList;

	List<DayEntity> dayEntityList;

	int lectureIndex;

	DaysInLectures daysInLectures;
	List<DaysResponse> daysResponseList;
	DaysResponse daysResponse;

	LectureEntityStatus lectureEntityStatus;

	UtilMethods utilMethods = new UtilMethods();

	final String failureMessage = "Lecture is not added !";
	final String successMessage = " Lecture is added successfully !";

	final String failureRemoveLectureMessage = "lecture not deleted successfully !";
	final String successRemoveLectureMessage = " lecture deleted successfully !";

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

		courseEntityList = courseRepository.findAll();

		dayEntityList = courseEntity.getDayEntity();

		try {

			lectureEntity = lectureRepository.findByLectureNameAndStartTimeAndEndTime(
					createLectureRequest.getLectureName(), createLectureRequest.getStartTime(),
					createLectureRequest.getEndTime());

		} catch (Exception e) {
			log.info("Not able to create Lecture Entity! ");
		}

		Map<Integer, Integer> dayIdMap = new HashMap<Integer, Integer>();

		Integer fromDayId = createLectureRequest.getFromDayId();
		Integer toDayId = createLectureRequest.getToDayId();

		for (DayId dayId : createLectureRequest.getDayIds()) {
			dayEntity = findDayEntity(dayId.getDayId());
			dayIdMap.put(dayEntity.getDayId(), dayEntity.getDayId());
		}

		if (!Objects.isNull(fromDayId) && !Objects.isNull(toDayId)) {
			// from
			for (DayEntity dayEntity : dayEntityList) {
				if (dayEntity.getDayId() >= fromDayId && (dayEntity.getDayId() <= toDayId)) {
					dayIdMap.put(dayEntity.getDayId(), dayEntity.getDayId());
				}

			}
		}

		for (Map.Entry<Integer, Integer> entry : dayIdMap.entrySet()) {

			dayEntity = findDayEntity(entry.getKey());

			lectureEntityList = dayEntity.getLecEntity();
			lectureEntityStatus = new LectureEntityStatus();
			lectureEntityStatus.setLectureIsPresent(false);

			if (lectureEntityList.size() != 0) {

				if (!Objects.isNull(lectureEntity)) {

					if (!Objects.isNull(lectureEntity.getLectureName()) && !Objects.isNull(lectureEntity.getStartTime())
							&& !Objects.isNull(lectureEntity.getEndTime())) {

						lectureEntityStatus = lectureIsPresent(lectureEntityList, lectureEntity);

						if (lectureEntityStatus.isLectureIsPresent()) {

							lectureEntity = updateLecture(createLectureRequest);

							lectureIndex = lectureEntityStatus.getIndex();
							lectureEntityList.set(lectureIndex, lectureEntity);

						}
					}
				}

				if (!lectureEntityStatus.isLectureIsPresent()) {
					lectureEntity = lecture(createLectureRequest);
					lectureEntityList.add(lectureEntity);

				}

			} else {

				lectureEntityList = new ArrayList<LectureEntity>();
				lectureEntity = lecture(createLectureRequest);
				lectureEntityList.add(lectureEntity);
			}

			createDayEntity();

			statusMessageResponse = createCourseEntity();

		}

		return statusMessageResponse;

	}

	private LectureEntityStatus lectureIsPresent(List<LectureEntity> lectureEntityList, LectureEntity lectureEntity) {

		String lectureName = lectureEntity.getLectureName();
		String startTime = lectureEntity.getStartTime();
		String endTime = lectureEntity.getEndTime();

		lectureEntityStatus.setLectureIsPresent(false);

		for (int i = 0; i < lectureEntityList.size(); i++) {

			LectureEntity lecEntity = lectureEntityList.get(i);

			String lecName = lecEntity.getLectureName();
			String startTim = lecEntity.getStartTime();
			String endTim = lecEntity.getEndTime();

			if (lecName.equals(lectureName) && startTim.equals(startTime) && endTim.equals(endTime)) {

				lectureEntityStatus.setIndex(i);
				lectureEntityStatus.setLectureIsPresent(true);
				return lectureEntityStatus;

			}

		}

		return lectureEntityStatus;
	}

	private void createDayEntity() {
		dayEntity.setLecEntity(lectureEntityList);
		int index = dayEntityList.indexOf(dayEntity);
		dayEntityList.set(index, dayEntity);
	}

	private StatusMessageResponse createCourseEntity() {
		if (!Objects.isNull(courseEntity)) {
			int index = courseEntityList.indexOf(courseEntity);
			courseEntity.setDayEntity(dayEntityList);
			courseEntityList.set(index, courseEntity);
			courseRepository.saveAll(courseEntityList);
			return utilMethods.successResponse(successMessage);
		} else {
			return utilMethods.errorResponse(failureMessage);
		}
	}

	private LectureEntity lecture(CreateLectureTempRequest createLectureRequest) {

		if (Objects.isNull(lectureEntity)) {
			lectureEntity = new LectureEntity();
			lectureEntity = createLectureList(createLectureRequest, lectureEntity);
		} else {
			lectureEntity = createLectureList(createLectureRequest, lectureEntity);
		}
		return lectureEntity;
	}

	private void iframe(CreateLectureTempRequest LectByDay, LectureEntity lectureEntity) {

		boolean checkVideoIframeDynamicLink = false;
		boolean checkLiveIframeDynamicLink = false;

		if (Objects.isNull(LectByDay.getLiveIFrameLink())) {
			checkVideoIframeDynamicLink = LectByDay.getVideoIFrameLink().isEmpty();

		} else {
			checkLiveIframeDynamicLink = LectByDay.getLiveIFrameLink().isEmpty();
		}

		if (checkVideoIframeDynamicLink && checkLiveIframeDynamicLink) {
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

	private LectureEntity updateLecture(CreateLectureTempRequest createLectureRequest) {

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

	//
	public StatusMessageResponse removeLecture(Integer lectureId) {

		if (Objects.isNull(lectureId)) {
			utilMethods.errorResponse(failureMessage);
		}

		try {

			lectureEntity = lectureRepository.getLecEntityByLecId(lectureId);
		} catch (Exception e) {
			log.info("Not able to remove Lecture Entity! ");
		}

		if (Objects.isNull(lectureEntity)) {
			utilMethods.errorResponse(failureRemoveLectureMessage);
		}

		try {

			lectureRepository.delete(lectureEntity);
		} catch (Exception e) {
			log.info("Not able to remove Lecture Entity! ");
		}

		return utilMethods.successResponse(successRemoveLectureMessage);
	}

	public LectureListInCourse errorResponselistOfLectures() {
		lectureListInCourse = new LectureListInCourse();

		lectureListInCourse.setMessage("Empty Lecture! ");
		lectureListInCourse.setStatus(ApiConstants.FAILURE);
		return lectureListInCourse;
	}

	public DaysInLectures errorResponselistOfDaysInLectures() {
		daysInLectures = new DaysInLectures();

		daysInLectures.setMessage("Error in selected days in lecture! ");
		daysInLectures.setStatus(ApiConstants.FAILURE);
		return daysInLectures;
	}

	public LectureListInCourse listOfLectures(Integer courseId) {

		if (Objects.isNull(courseId)) {
			return errorResponselistOfLectures();
		}

		try {
			courseEntity = courseRepository.getCourseEntityByCourseId(courseId);
		} catch (Exception e) {
			return errorResponselistOfLectures();
		}

		if (Objects.isNull(courseEntity)) {
			return errorResponselistOfLectures();
		}

		dayEntityList = courseEntity.getDayEntity();

		lectureListInCourse = new LectureListInCourse();

		List<LectureReference> lecture = new ArrayList<>();

		LectureReference lectureReference;

		lectureListInCourse = new LectureListInCourse();

		for (DayEntity dayEntity : dayEntityList) {

			for (LectureEntity lectureEntity : dayEntity.getLecEntity()) {
				lectureReference = new LectureReference();
				lectureReference.setLectureId(lectureEntity.getLectureId());
				lectureReference.setLectureName(lectureEntity.getLectureName());
				lecture.add(lectureReference);
			}

		}

		lectureListInCourse.setLecture(lecture);
		lectureListInCourse.setMessage("");
		lectureListInCourse.setStatus(ApiConstants.SUCCESS);

		return lectureListInCourse;
	}

	public DaysInLectures getSelectedDaysInLecture(Integer lectureId, Integer courseId) {

		if (Objects.isNull(courseId)) {
			return errorResponselistOfDaysInLectures();
		}

		try {
			courseEntity = courseRepository.getCourseEntityByCourseId(courseId);
		} catch (Exception e) {
			return errorResponselistOfDaysInLectures();
		}

		if (Objects.isNull(courseEntity)) {
			errorResponselistOfDaysInLectures();
		}

		try {

			lectureEntity = lectureRepository.getLecEntityByLecId(lectureId);
		} catch (Exception e) {
			return errorResponselistOfDaysInLectures();

		}

		if (Objects.isNull(lectureEntity)) {
			return errorResponselistOfDaysInLectures();
		}

		dayEntityList = courseEntity.getDayEntity();
		daysInLectures = new DaysInLectures();
		daysResponseList = new ArrayList<>();

		for (DayEntity dayEntity : dayEntityList) {

			lectureEntityList = dayEntity.getLecEntity();
			
			System.out.println("###########      Day ID #########"+ dayEntity.getDayId());
			System.out.println(" $$$$$$$$$$$$$$  Day Name $$$$$$$$$$ "+ dayEntity.getDayName());


			System.out.println("###########      Required Lecture  #########"+ lectureEntity.getLectureName());
			
			
			if (lectureEntityList.contains(lectureEntity)) {
				daysResponse = new DaysResponse();
				daysResponse.setDayId(dayEntity.getDayId());
				daysResponse.setDayName(dayEntity.getDayName());
				daysResponseList.add(daysResponse);
			}

		}

		if (!Objects.isNull(daysResponseList)) {
			daysInLectures.setDaysResponse(daysResponseList);
			daysInLectures.setStatus(ApiConstants.SUCCESS);
			daysInLectures.setMessage("List of days");
		}

		return daysInLectures;
	}

}