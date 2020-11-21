//package com.yoga.api.service;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Objects;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.yoga.api.entity.CourseEntity;
//import com.yoga.api.entity.DayEntity;
//import com.yoga.api.entity.LectureDetailReferenceEntity;
//import com.yoga.api.entity.LectureEntity;
//import com.yoga.api.entity.LectureSessionEntity;
//import com.yoga.api.entity.LectureTypeEntity;
//import com.yoga.api.model.AddCourseByDayId;
//import com.yoga.api.model.DayByCourseId;
//import com.yoga.api.model.LectureByDay;
//import com.yoga.api.model.LectureReference;
//import com.yoga.api.model.LectureSession;
//import com.yoga.api.model.LectureSessionReference;
//import com.yoga.api.model.LectureType;
//import com.yoga.api.model.StatusMessageResponse;
//import com.yoga.api.repository.CourseRepository;
//import com.yoga.api.repository.DayRepository;
//import com.yoga.api.repository.LectureReferenceRepository;
//import com.yoga.api.repository.LectureRepository;
//import com.yoga.api.repository.LectureSessionRepository;
//import com.yoga.api.repository.LectureTypeRepository;
//import com.yoga.api.util.UtilMethods;
//
//@Service
//public class AddCourseWithLectureDetailsService {
//
//	@Autowired
//	CourseRepository courseRepository;
//
//	@Autowired
//	DayRepository dayRepository;
//
//	@Autowired
//	LectureRepository lectureRepository;
//
//	CourseEntity courseEntity;
//
//	StatusMessageResponse statusMessageResponse = new StatusMessageResponse();
//
//	DayByCourseId dayByCourseId;
//	DayEntity dayEntity;
//	List<DayEntity> dayEntityList;
//
//	LectureByDay LectureByDay;
//	LectureEntity lectureEntity;
//	List<LectureEntity> lecEntityList;
//
//	List<List<LectureEntity>> lecEntityListOfList;
//
//	UtilMethods utilMethods = new UtilMethods();
//
//	// new add
//
//	List<LectureType> lectureTypeList;
//
//	LectureType lectureType;
//
//	List<LectureSession> lectureSessionList;
//
//	List<LectureSessionReference> lectureSessionReferenceList;
//
//	LectureSessionReference lectureSessionReference;
//
//	@Autowired
//	LectureReferenceRepository lectureReferenceRepository;
//
//	@Autowired
//	LectureSessionRepository lectureSessionRepository;
//
//	@Autowired
//	LectureTypeRepository lectureTypeRepository;
//
//	LectureDetailReferenceEntity lectureDetailReferenceEntity;
//
//	List<LectureDetailReferenceEntity> lectureDetailReferenceEntityList;
//
//	LectureSessionEntity lectureSessionEntity;
//
//	List<LectureSessionEntity> lectureSessionEntityList;
//
//	LectureTypeEntity lectureTypeEntity;
//
//	List<LectureTypeEntity> lectureTypeEntityList;
//
//	LectureReference lectureReference;
//
//	List<LectureReference> lectureReferenceList;
//
//	// Add Course api
//	public StatusMessageResponse addCourseWithLectureDetails(AddCourseByDayId course) throws InterruptedException {
//
//		final String failureMessage = "course is not created !";
//		final String successMessage = " course have created successfully !";
//		
//		// condition for error response
//		if (Objects.isNull(course)) {
//
//			return utilMethods.errorResponse(failureMessage);
//		}
//
//		try {
//			courseEntity = courseRepository.getCourseByCourseNameAndStartDateAndCouseDuration(course.getCourseName(),
//					course.getStartDate(), course.getDay().size());
//		} catch (Exception e) {
//
//			return utilMethods.errorResponse(failureMessage);
//
//		}
//
//		if (!Objects.isNull(courseEntity)) {
//			return utilMethods.errorResponse(failureMessage);
//
//		}
//
//		if (Objects.isNull(course.getDay())) {
//
//			return utilMethods.errorResponse(failureMessage);
//
//		}
//
//		lecEntityListOfList = new ArrayList<>();
//		dayEntityList = new ArrayList<>();
//
//		for (DayByCourseId day : course.getDay()) {
//
//			lecEntityList = new ArrayList<>();
//
//			for (LectureByDay LectByDay : day.getLecture()) {
//				lectureEntity = new LectureEntity();
//				createLectureList(LectByDay);
//			}
//
//			dayEntity = new DayEntity();
//
//			if (!Objects.isNull(lecEntityList)) {
//				lectureRepository.saveAll(lecEntityList);
//				dayEntity.setLecEntity(lecEntityList);
//
//			}
//
//			dayEntity.setDayName(day.getDayName());
//			dayEntityList.add(dayEntity);
//		}
//
//		if (!Objects.isNull(dayEntityList)) {
//			dayRepository.saveAll(dayEntityList);
//		}
//
//		saveCourse(course);
//
//		return utilMethods.successResponse(successMessage);
//
//	}
//
//	private void saveCourse(AddCourseByDayId course) {
//		try {
//			courseEntity = new CourseEntity();
//			courseEntity.setCourseName(course.getCourseName());
//			courseEntity.setCouseDuration(dayEntityList.size());
//
//			courseEntity.setDayEntity(dayEntityList);
//			courseEntity.setStartDate(course.getStartDate());
//
//			if (!Objects.isNull(courseEntity)) {
//				courseRepository.save(courseEntity);
//			}
//		} catch (Exception e) {
//
//		}
//	}
//
//	private void createLectureList(LectureByDay LectByDay) {
//		if (!Objects.isNull(LectByDay)) {
//
//			// set video and live iframe link
//			iframe(LectByDay);
//
//			lectureEntity.setSNo(LectByDay.getSNo());
//			lectureEntity.setCurrDate(LectByDay.getCurrentDate().toUpperCase());
//			lectureEntity.setEndTime(LectByDay.getEndTime().toUpperCase());
//			lectureEntity.setLectureName(LectByDay.getLectureName().toUpperCase());
//			lectureEntity.setStartTime(LectByDay.getStartTime().toUpperCase());
//			lectureEntity.setLectureTypeEntity(lectureTypeObject(LectByDay));
//
//			lecEntityList.add(lectureEntity);
//
//		}
//	}
//
//	private void iframe(LectureByDay LectByDay) {
//
//		if (Objects.isNull(LectByDay.getVideoIframeDynamicLink())
//				&& Objects.isNull(LectByDay.getLiveIframeDynamicLink())) {
//			lectureEntity.setDisableJoinBtn("true");
//			lectureEntity.setLiveIframeDynamicLink(null);
//			lectureEntity.setVideoIframeDynamicLink(null);
//
//		} else if (!Objects.isNull(LectByDay.getVideoIframeDynamicLink())
//				&& !Objects.isNull(LectByDay.getLiveIframeDynamicLink())) {
//
//			lectureEntity.setDisableJoinBtn("false");
//			lectureEntity.setLiveIframeDynamicLink(LectByDay.getLiveIframeDynamicLink());
//			lectureEntity.setVideoIframeDynamicLink(LectByDay.getVideoIframeDynamicLink());
//
//		} else {
//
//			if (!Objects.isNull(lectureEntity.getVideoIframeDynamicLink())) {
//				lectureEntity.setVideoIframeDynamicLink(LectByDay.getVideoIframeDynamicLink());
//				lectureEntity.setDisableJoinBtn("false");
//				lectureEntity.setLiveIframeDynamicLink(null);
//
//			} else {
//				lectureEntity.setVideoIframeDynamicLink(null);
//				lectureEntity.setDisableJoinBtn("false");
//				lectureEntity.setLiveIframeDynamicLink(LectByDay.getLiveIframeDynamicLink());
//
//			}
//
//		}
//
//	}
//
//	// upper object
//	private List<LectureTypeEntity> lectureTypeObject(com.yoga.api.model.LectureByDay lectByDay) {
//
//		List<LectureTypeEntity> lectureTypeEntityList = new ArrayList<>();
//
//		for (LectureType lectureTypeNew : lectByDay.getLectureType()) {
//
//			lectureTypeEntity = new LectureTypeEntity();
//
//			String lectureTypeHeaderName = lectureTypeNew.getLectureTypeHeaderName();
//			lectureTypeEntity.setLectureTypeHeaderName(lectureTypeHeaderName);			
//			lectureTypeEntity.setLectureSessionEntity(lectureSessionObject(lectByDay));
//
//			lectureTypeEntityList.add(lectureTypeEntity);
//
//		}
//
//		return lectureTypeEntityList;
//
//	}
//
//	private List<LectureSessionEntity> lectureSessionObject(com.yoga.api.model.LectureByDay lectByDay) {
//
//		lectureSessionEntityList = new ArrayList<>();
//
//		for (LectureSession lectureSession : lectureSessionList) {
//
//			lectureSessionEntity = new LectureSessionEntity();
//
//			lectureSessionEntity.setLectureDetailReferenceEntity(lectureReferenceObject(lectByDay));
//			lectureSessionEntity.setRound(lectureSession.getRound());
//			lectureSessionEntity.setName(lectureSession.getName());
//			
//			lectureSessionEntityList.add(lectureSessionEntity);
//
//		}
//		return lectureSessionEntityList;
//
//	}
//
//	private List<LectureDetailReferenceEntity> lectureReferenceObject(com.yoga.api.model.LectureByDay lectByDay) {
//
//		for (LectureReference lectureReference : lectureReferenceList) {
//			
//			lectureDetailReferenceEntity = new LectureDetailReferenceEntity();
//			lectureDetailReferenceEntity.setReference(lectureReference.getReference());
//			lectureDetailReferenceEntityList.add(lectureDetailReferenceEntity);
//		}
//		return lectureDetailReferenceEntityList;
//
//	}
//	
//	public List<LectureSessionEntity> lectureSessionList(){
//		
//		return lectureSessionRepository.findAll();
//		
//	}
//
//}