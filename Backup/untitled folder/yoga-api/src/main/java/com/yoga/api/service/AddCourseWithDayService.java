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
//import com.yoga.api.entity.DaysEntity;
//import com.yoga.api.entity.LecEntity;
//import com.yoga.api.model.Course;
//import com.yoga.api.model.CourseDays;
//import com.yoga.api.model.Lecture;
//import com.yoga.api.repository.CourseRepository;
//import com.yoga.api.repository.DayRepository;
//import com.yoga.api.repository.LecRepository;
//
//@Service
//public class AddCourseWithDayService {
//
//	@Autowired
//	CourseRepository courseRepository;
//
//	@Autowired
//	LecRepository lectureRepository;
//
//	@Autowired
//	DayRepository dayRepository;
//
//	CourseEntity courseEntity;
//
//	List<LecEntity> lecEntityList;
//
//	LecEntity lectureEntity;
//
//	List<CourseDays> courseDaysList;
//
//	DaysEntity daysEntity;
//
//	List<DaysEntity> daysEntityList;
//
//	// Add Course api
//	public CourseEntity addCourse(Course course) {
//
//		String dayName = "DAY ";
//
//		courseEntity = courseRepository.getCourseByCourseNameAndStartDate(course.getCourseName(),
//				course.getStartDate());	
//		
//			
//		if (Objects.isNull(courseEntity)) {
//			
//			lecEntityList = new ArrayList<>();
//			courseDaysList = new ArrayList<>();		
//			daysEntityList = new ArrayList<>();
//	
//			
//			for(DaysEntity daysEntity:  daysEntityList ) {	
//				
//				daysEntityList = new ArrayList<>();
//				daysEntityList = dayRepository.getDayEntityListByDayId(courseEntity.getDayId());
//
//				
//				//lectureRepository.getLecEntityByLecId(daysEntity.getLecId())
////				
////				for(LecEntity lecEntity: ) {
////					
////				}
//				
//				
//				
//				
//				
//				
//			}
//
//			for (CourseDays courseDays1 : course.getCourseDays()) {
//
//				int dayNumber = 1;
//				courseDays1 = new CourseDays();
//				daysEntity = new DaysEntity();
//
//				for (Lecture lecture1 : courseDays1.getLecture()) {
//
//					lectureEntity = new LecEntity();
//
//					lectureEntity.setCurrDate(lecture1.getCurrentDate().toUpperCase());
//					lectureEntity.setDisableJoinBtn(false);
//					lectureEntity.setEndTime(lecture1.getEndTime().toUpperCase());
//					lectureEntity.setLectureName(lecture1.getLectureName().toUpperCase());
//					lectureEntity.setStartTime(lecture1.getStartTime().toUpperCase());
//					lecEntityList.add(lectureEntity);
//				}
//
//				// daysEntity.setDayName(dayName + dayNumber);
//				// daysEntity.setLectureEntity(lecEntityList);
//
//				dayNumber++;
//				daysEntityList.add(daysEntity);
//
//			}
//		}
//
//		if (!Objects.isNull(lecEntityList)) {
//			lectureRepository.saveAll(lecEntityList);
//		}
//
//		if (!Objects.isNull(courseDaysList)) {
//			dayRepository.saveAll(daysEntityList);
//		}
//
//		courseEntity = new CourseEntity();
//
//		courseEntity.setCourseName(course.getCourseName().toUpperCase());
//		courseEntity.setCouseDuration(course.getCouseDuration());
//		// courseEntity.set
//		// courseEntity.setDaysEntity(daysEntityList);
//		// courseEntity.setStartDate(course.getStartDate().toUpperCase());
//
//		if (!Objects.isNull(courseEntity)) {
//
//			courseRepository.save(courseEntity);
//
//			return courseEntity;
//
//		}
//
//		return courseEntity;
//	}
//
//}
