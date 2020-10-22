package com.yoga.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yoga.api.entity.CourseEntity;
import com.yoga.api.entity.DayEntity;
import com.yoga.api.entity.LectureEntity;
import com.yoga.api.model.AddCourseByDayId;
import com.yoga.api.model.DayByCourseId;
import com.yoga.api.model.LectureByDay;
import com.yoga.api.model.StatusMessageResponse;
import com.yoga.api.repository.CourseRepository;
import com.yoga.api.repository.DayRepository;
import com.yoga.api.repository.LectureRepository;

@Service
public class AddCourseFromAdminService {

	@Autowired
	CourseRepository courseRepository;

	@Autowired
	DayRepository dayRepository;

	@Autowired
	LectureRepository lectureRepository;

	CourseEntity courseEntity;

	StatusMessageResponse statusMessageResponse = new StatusMessageResponse();

	DayByCourseId dayByCourseId;
	DayEntity dayEntity;
	List<DayEntity> dayEntityList;

	LectureByDay LectureByDay;
	LectureEntity lectureEntity;
	List<LectureEntity> lecEntityList;

	List<List<LectureEntity>> lecEntityListOfList;

	// Add Course api
	public StatusMessageResponse addCourseFromAdmin(AddCourseByDayId course) throws InterruptedException {

		courseEntity = courseRepository.getCourseByCourseNameAndStartDateAndCouseDuration(course.getCourseName(),
				course.getStartDate(), course.getCouseDuration());

		if (Objects.isNull(courseEntity)) {

			lecEntityListOfList = new ArrayList<>();
			dayEntityList = new ArrayList<>();

			for (DayByCourseId day : course.getDay()) {
				
				lecEntityList = new ArrayList<>();
			//Thread.sleep(1000);

	
				for (LectureByDay LectByDay : day.getLecture()) {

					lectureEntity = new LectureEntity();
					
					lectureEntity.setSNo(LectByDay.getSNo());
					lectureEntity.setCurrDate(LectByDay.getCurrentDate().toUpperCase());
					lectureEntity.setDisableJoinBtn(false);
					lectureEntity.setEndTime(LectByDay.getEndTime().toUpperCase());
					lectureEntity.setLectureName(LectByDay.getLectureName().toUpperCase());
					lectureEntity.setStartTime(LectByDay.getStartTime().toUpperCase());
					lectureEntity.setIframeDynamicLink(LectByDay.getIFrameDynamicLink());
					//Thread.sleep(1000);
					lecEntityList.add(lectureEntity);
					lecEntityListOfList.add(lecEntityList);

				}
				//Thread.sleep(1000);

				
				lectureRepository.saveAll(lecEntityList);

				//Thread.sleep(1000);

				dayEntity = new DayEntity();
				dayEntity.setLecEntity(lecEntityList);
				System.out.println("Day name "+day.getDayName());
				dayEntity.setDayName(day.getDayName());
				//Thread.sleep(1000);

				dayEntityList.add(dayEntity);
				
				System.out.println(dayEntity.getDayName());
			}

			if (!Objects.isNull(dayEntityList)) {

			dayRepository.saveAll(dayEntityList);
			
			 }

			courseEntity = new CourseEntity();
			courseEntity.setCourseName(course.getCourseName());
			courseEntity.setCouseDuration(course.getCouseDuration());
			courseEntity.setDayEntity(dayEntityList);
			courseEntity.setStartDate(course.getStartDate());

			if (!Objects.isNull(courseEntity)) {
				courseRepository.save(courseEntity);
			}
			
			
//			courseEntity = courseRepository.getCourseByCourseNameAndStartDateAndCouseDuration(course.getCourseName(),
//					course.getStartDate(), course.getCouseDuration());
//			
//			courseEntity = courseRepository.getCourseEntityByCourseId(courseEntity.getCourseId());



			statusMessageResponse.setMessage("course have created successfully ");
			statusMessageResponse.setStatus("success");

			return statusMessageResponse;

		} else {

			statusMessageResponse.setMessage("course is not created");
			statusMessageResponse.setStatus("fail");

			return statusMessageResponse;

		}

	}

}