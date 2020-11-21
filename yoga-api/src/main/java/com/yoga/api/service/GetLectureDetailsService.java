//package com.yoga.api.service;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.yoga.api.entity.LectureDetailReferenceEntity;
//import com.yoga.api.entity.LectureEntity;
//import com.yoga.api.entity.LectureSessionEntity;
//import com.yoga.api.entity.LectureTypeEntity;
//import com.yoga.api.model.CourseLectureDetailResponse;
//import com.yoga.api.model.LectureSession;
//import com.yoga.api.model.LectureType;
//import com.yoga.api.repository.LectureReferenceRepository;
//import com.yoga.api.repository.LectureRepository;
//import com.yoga.api.repository.LectureSessionRepository;
//import com.yoga.api.repository.LectureTypeRepository;
//
//@Service
//public class GetLectureDetailsService {
//
//	@Autowired
//	LectureReferenceRepository lectureReferenceRepository;
//
//	@Autowired
//	LectureRepository lectureRepository;
//
//	@Autowired
//	LectureSessionRepository lectureSessionRepository;
//
//	@Autowired
//	LectureTypeRepository lectureTypeRepository;
//
//	CourseLectureDetailResponse courseLectureDetailResponse;
//
//	LectureDetailReferenceEntity lectureDetailReferenceEntity;
//
//	LectureEntity lectureEntity;
//
//	LectureSessionEntity lectureSessionEntity;
//
//	LectureTypeEntity lectureTypeEntity;
//
//	List<LectureSessionEntity> lectureSessionEntityList;
//
//	List<LectureTypeEntity> lectureTypeEntityList;
//	
//	LectureType lectureType;
//
//	public CourseLectureDetailResponse getLecturesDetails(Integer lectureId, String lectureName) {
//
//		courseLectureDetailResponse = new CourseLectureDetailResponse();
//
//		lectureTypeEntity = lectureTypeRepository.getLectureTypeByLectureId(lectureId);
//		List<LectureType> lectureTypeListObj = createLectureTypeObject(lectureTypeEntity);
//		courseLectureDetailResponse.setLectureType(lectureTypeListObj);
//
//		return courseLectureDetailResponse;
//	}
//
//	private List<LectureType> createLectureTypeObject(LectureTypeEntity lecTypeEntity) {
//
//				
//		//lectureSessionEntity = lectureSessionRepository.getLectureSessionEntityLectureTypeId(lecTypeEntity.lectureTypeId);
//
//		String lectureTypeHeaderName = lectureTypeEntity.getLectureTypeHeaderName();
//		lectureType.setLectureTypeHeaderName(lectureTypeHeaderName);
//
//		List<LectureSession> lectureSessionListObj = createLectureSessionObject(lectureSessionEntity);
//		lectureType.setLectureSession(lectureSessionListObj);
//
//		return null;
//	}
//		
//	private List<LectureSession> createLectureSessionObject(LectureSessionEntity lectureSessionEntity) {
//		
//	//	lectureSessionEntity = lectureSessionRepository.getLectureSessionEntityLectureTypeId(lecTypeEntity.lectureTypeId);
//		
//		return null;
//	}
//	
//	
//
//}
