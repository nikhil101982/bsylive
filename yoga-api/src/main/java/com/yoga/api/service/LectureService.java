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

import lombok.extern.slf4j.Slf4j;

// create lecture api

@Service
@Slf4j
public class LectureService {
	
	
	 
	
// If Lecture is null is not present then 
	
	

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
		
		courseEntityList = courseRepository.findAll();


		dayEntityList = courseEntity.getDayEntity();

		lecture(createLectureRequest);

		for (DayId dayId : createLectureRequest.getDayIds()) {

			lecture(createLectureRequest);

			dayEntity = findDayEntity(dayId.getDayId());
			
			if(!Objects.isNull(dayEntity.getLecEntity())) {
				lectureEntityList = dayEntity.getLecEntity();
				
				int index1 = lectureEntityList.indexOf(lectureEntity);
				
				if(index1==-1) {
					lectureEntityList.add(0,lectureEntity);
				}else {
					lectureEntityList.set(index1, lectureEntity);
				}
				
			}else {
				lectureEntityList = new ArrayList<LectureEntity>();
				lectureEntityList.add(lectureEntity);
			}
			
			



			
			dayEntity.setLecEntity(lectureEntityList);

			int index = dayEntityList.indexOf(dayEntity);
			
			dayEntityList.set(index, dayEntity);
		}

		if (!Objects.isNull(dayEntityList)) {

			for (int i = 0; i < dayEntityList.size(); i++) {
				DayEntity dEntity = dayEntityList.get(i);
				if (!(dEntity.getLecEntity().size() == 0)) {
					System.out.println("Day entity Lecture ********* Days name  " + dEntity.dayName + "Lecture entity"
							+ dEntity.getLecEntity());
				}
			}

			//updateAllDayRepository(dayEntityList);
		}

		if (!Objects.isNull(courseEntity)) {
			
			if(!Objects.isNull(dayEntity.getLecEntity())) {
				
				int index1 = courseEntityList.indexOf(courseEntity);
				
				courseEntity.setDayEntity(dayEntityList);
				
				courseEntityList.set(index1, courseEntity);
				
				
				courseRepository.saveAll(courseEntityList);

				
	
				
			}
			

//			for (int i = 0; i < dayEntityList.size(); i++) {
//
//				DayEntity d = dayEntityList.get(i);
//
//				if (!(d.getLecEntity().size() == 0)) {
//					System.out.println("Day entity Lecture ********* Days name  " + d.dayName + "Lecture entity"
//							+ d.getLecEntity());
//				}
//
//			}

			// for(DayEntity dayEntity: dayEntityList){
			//
			// System.out.println(dayEntity);
			// dayRepository.save(dayEntity);
			// }

			// courseRepository.save(courseEntity);
			
			//courseRepository.delete(courseEntity);
			
			//courseRepository.updateCourseEntity(dayEntityList, courseEntity.getCourseId());

			return utilMethods.successResponse(successMessage);

		} else {
			return utilMethods.errorResponse(failureMessage);

		}

	}

	private void lecture(CreateLectureTempRequest createLectureRequest) {
		try {
			lectureEntity = lectureRepository.getLectureByLectureNameAndStartTimeAndEndTime(
					createLectureRequest.getLectureName(), createLectureRequest.getStartTime(),
					createLectureRequest.getEndTime());

			System.out.println("******************* LectureEntity ************" + lectureEntity);

		} catch (Exception e) {
			log.info("Not able to create Lecture Entity! ");

		}

		if (Objects.isNull(lectureEntity)) {
			lectureEntity = new LectureEntity();
			lectureEntity = addLecture(createLectureRequest, lectureEntity);
			//saveLectureEntity(lectureEntity);
		} else {
			lectureEntity = addLecture(createLectureRequest, lectureEntity);
			//updateLectureEntity(lectureEntity);
		}
	}

	private LectureEntity addLecture(CreateLectureTempRequest createLectureRequest, LectureEntity lectureEntity) {

		lectureEntity = createLectureList(createLectureRequest, lectureEntity);
		return lectureEntity;

	}

	private void updateAllDayRepository(List<DayEntity> dayEntityList) {
		try {

			for (int i = 0; i < dayEntityList.size(); i++) {

				DayEntity dEnt = dayEntityList.get(i);

				dayRepository.updateDayEntity(dEnt.getLecEntity(), dEnt.getDayId());

				System.out.println("Day entity Lecture ********* " + dEnt.getLecEntity());

			}

			for (DayEntity DayEntity : dayEntityList) {
				if (!(DayEntity.getLecEntity().size() == 0)) {

					dayRepository.save(DayEntity);

					// dayRepository.updateDayEntity(DayEntity.getLecEntity(),
					// DayEntity.getDayId());

				}
			}

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

	private void updateLectureEntity(LectureEntity lectureEntity) {
		try {
			lectureRepository.updateLectureEntity(lectureEntity.getLectureName(), lectureEntity.getStartTime(),
					lectureEntity.getEndTime(), lectureEntity.getVideoIframeDynamicLink(),
					lectureEntity.getLiveIframeDynamicLink(), lectureEntity.getCurrDate(),
					lectureEntity.getLectureId());

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