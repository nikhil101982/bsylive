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
import com.yoga.api.model.DaysResponse;
import com.yoga.api.model.LectureEntityStatus;
import com.yoga.api.model.ListOfLectures;
import com.yoga.api.model.ListOfLecturesResponse;
import com.yoga.api.model.SelectedDayResponse;
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

	SelectedDayResponse selectedDayResponse;

	DayEntity dayEntity;

	StatusMessageResponse statusMessageResponse = new StatusMessageResponse();

	LectureEntity lectureEntity;

	List<LectureEntity> lectureEntityList;

	List<DayEntity> dayEntityList;

	ListOfLecturesResponse listOfLecturesResponse = new ListOfLecturesResponse();

	List<SelectedDayResponse> selectedDayResponseList;

	List<DaysResponse> daysResponseList;

	int lectureIndex;

	LectureEntityStatus lectureEntityStatus;

	ListOfLectures listOfLectures;

	List<ListOfLectures> listOfLectureList = new ArrayList<>();

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

		if (!Objects.isNull(LectByDay.getVideoIFrameLink())) {
			lectureEntity.setVideoIframeDynamicLink(LectByDay.getLiveIFrameLink());
			lectureEntity.setDisableJoinBtn("false");
			lectureEntity.setLiveIframeDynamicLink(null);

		} else {
			lectureEntity.setVideoIframeDynamicLink(null);
			lectureEntity.setDisableJoinBtn("false");
			lectureEntity.setLiveIframeDynamicLink(LectByDay.getLiveIFrameLink());

		}

	}

	private LectureEntity createLectureList(CreateLectureTempRequest createLectureRequest,
			LectureEntity lectureEntity) {

		if (!Objects.isNull(createLectureRequest)) {
			iframe(createLectureRequest, lectureEntity);
			lectureEntity.setEndTime(createLectureRequest.getEndTime().toUpperCase());
			lectureEntity.setLectureName(createLectureRequest.getLectureName());
			lectureEntity.setStartTime(createLectureRequest.getStartTime().toUpperCase());
			lectureEntity.setFromDay(createLectureRequest.getFromDayId());
			lectureEntity.setToDay(createLectureRequest.getToDayId());
		}
		return lectureEntity;
	}

	private LectureEntity updateLecture(CreateLectureTempRequest createLectureRequest) {

		if (!Objects.isNull(createLectureRequest)) {
			iframe(createLectureRequest, lectureEntity);
			lectureEntity.setEndTime(createLectureRequest.getEndTime().toUpperCase());
			lectureEntity.setLectureName(createLectureRequest.getLectureName());
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

	public ListOfLecturesResponse errorListOfLectureResponse(String message) {

		listOfLecturesResponse.setMessage(message);
		listOfLecturesResponse.setStatus(ApiConstants.SUCCESS);

		return listOfLecturesResponse;
	}

	public ListOfLecturesResponse successListOfLectureResponse(String message) {

		listOfLecturesResponse.setMessage(message);
		listOfLecturesResponse.setStatus(ApiConstants.SUCCESS);
		listOfLecturesResponse.setLecture(listOfLectureList);

		return listOfLecturesResponse;

	}

	public StatusMessageResponse removeLecture(Integer lectureId, Integer dayId, Integer courseId) {

		if (Objects.isNull(lectureId) || Objects.isNull(dayId) || Objects.isNull(courseId)) {
			return utilMethods.errorResponse(failureMessage);
		}

		try {
			courseEntity = courseRepository.getCourseEntityByCourseId(courseId);
		} catch (Exception e) {
			return utilMethods.errorResponse(failureMessage);
		}

		if (Objects.isNull(courseEntity.getDayEntity())) {
			return utilMethods.errorResponse(failureMessage);
		}

		dayEntityList = courseEntity.getDayEntity();

		for (DayEntity dayEntity : dayEntityList) {

			if (dayEntity.getDayId().equals(dayId)) {

				lectureEntityList = dayEntity.getLecEntity();

				for (LectureEntity lectureEntity : lectureEntityList) {
					if (lectureEntity.getLectureId().equals(lectureId)) {
						lectureEntityList.remove(lectureEntityList.indexOf(lectureEntity));
					}

				}

			}
		}

		return utilMethods.successResponse(successMessage);
	}

	public ListOfLecturesResponse listOfLectures(Integer courseId) {

		if (Objects.isNull(courseId)) {
			return errorListOfLectureResponse("Error in list of lecture");
		}

		try {
			courseEntity = courseRepository.getCourseEntityByCourseId(courseId);
		} catch (Exception e) {
			return errorListOfLectureResponse("Error in list of lecture");
		}

		dayEntityList = new ArrayList<>();

		for (DayEntity dayEntity : courseEntity.getDayEntity()) {

			for (LectureEntity lectureEntity : dayEntity.getLecEntity()) {
				listOfLectures = new ListOfLectures();
				listOfLectures.setLectureId(lectureEntity.getLectureId());
				listOfLectures.setLectureName(lectureEntity.getLectureName());
				listOfLectureList.add(listOfLectures);
			}

		}

		listOfLecturesResponse.setLecture(listOfLectureList);

		return successListOfLectureResponse("List of lectures");
	}

	public SelectedDayResponse errorSelectedDayResponse(String message) {

		return selectedDayResponse;

	}

	public SelectedDayResponse successSelectedDayResponse(String message) {
		return selectedDayResponse;

	}

	public SelectedDayResponse selectedDaysId(Integer courseId, Integer lectureId) {

		if (Objects.isNull(courseId) && Objects.isNull(lectureId)) {
			return errorSelectedDayResponse("Error in Selected Days !");
		}

		try {
			courseEntity = courseRepository.getCourseEntityByCourseId(courseId);
		} catch (Exception e) {
			return errorSelectedDayResponse("Error in Selected Days !");
		}

		if (Objects.isNull(courseEntity)) {
			return errorSelectedDayResponse("Error in Selected Days !");
		}

		dayEntityList = courseEntity.getDayEntity();

		try {

			lectureEntity = lectureRepository.getLecEntityByLecId(lectureId);

		} catch (Exception e) {
			return errorSelectedDayResponse("Error in Selected Days !");
		}

		int index = dayEntityList.indexOf(lectureEntity);

		for (DayEntity dayEntity : dayEntityList) {

		}

		return null;
	}

}