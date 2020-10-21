package com.yoga.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yoga.api.entity.CourseEntity;
import com.yoga.api.entity.DayEntity;
import com.yoga.api.entity.LecEntity;
import com.yoga.api.model.AddCourseByDayId;
import com.yoga.api.model.DayByCourseId;
import com.yoga.api.model.LectureByDay;
import com.yoga.api.model.StatusMessageResponse;
import com.yoga.api.repository.CourseRepository;
import com.yoga.api.repository.DayRepository;
import com.yoga.api.repository.LecRepository;

@Service
public class CourseService {

	@Autowired
	CourseRepository courseRepository;

	@Autowired
	DayRepository dayRepository;

	@Autowired
	LecRepository lectureRepository;

	CourseEntity courseEntity;

	StatusMessageResponse statusMessageResponse = new StatusMessageResponse();

	DayByCourseId dayByCourseId;
	DayEntity dayEntity;
	List<DayEntity> dayEntityList;

	LectureByDay LectureByDay;
	LecEntity lectureEntity;
	List<LecEntity> lecEntityList;

	List<List<LecEntity>> lecEntityListOfList;

	// Add Course api
	public StatusMessageResponse addCourseFromAdmin(AddCourseByDayId course) {

		courseEntity = courseRepository.getCourseByCourseNameAndStartDateAndCouseDuration(course.getCourseName(),
				course.getStartDate(), course.getCouseDuration());

		if (Objects.isNull(courseEntity)) {

			lecEntityListOfList = new ArrayList<>();


			dayEntityList = new ArrayList<>();

			for (DayByCourseId day : course.getDay()) {
				
				lecEntityList = new ArrayList<>();


	
				for (LectureByDay LectByDay : day.getLecture()) {
										
					//lecEntityList = new ArrayList<>();
					
					int sNo = 1;


					lectureEntity = new LecEntity();
					
					lectureEntity.setSNo(LectByDay.getSNo());
					lectureEntity.setCurrDate(LectByDay.getCurrentDate().toUpperCase());
					lectureEntity.setDisableJoinBtn(false);
					lectureEntity.setEndTime(LectByDay.getEndTime().toUpperCase());
					lectureEntity.setLectureName(LectByDay.getLectureName().toUpperCase());
					lectureEntity.setStartTime(LectByDay.getStartTime().toUpperCase());
					lectureEntity.setIframeDynamicLink(LectByDay.getIFrameDynamicLink());
					// lectureRepository.save(lectureEntity);
					lecEntityList.add(lectureEntity);
					//lectureEntity.setSNo(sNo);
					lecEntityListOfList.add(lecEntityList);
					
					sNo = sNo+1;


				}
				
				lectureRepository.saveAll(lecEntityList);


				dayEntity = new DayEntity();
				dayEntity.setLecEntity(lecEntityList);
				dayEntity.setDayName(day.getDayName());
				
				dayEntityList.add(dayEntity);

				// lectureRepository.save(dayEntity);

				


				// dayRepository.save(dayEntity);

				// lecEntityList = new ArrayList<>();

				// dayEntityList = new ArrayList<>();
				

			}

			// if (!Objects.isNull(dayEntityList)) {
			//
			dayRepository.saveAll(dayEntityList);
			//
			// }

			// addLecturesDay(course);

			courseEntity = new CourseEntity();
			courseEntity.setCourseName(course.getCourseName());
			courseEntity.setCouseDuration(course.getCouseDuration());
			courseEntity.setDayEntity(dayEntityList);
			courseEntity.setStartDate(course.getStartDate());

			if (!Objects.isNull(courseEntity)) {
				courseRepository.save(courseEntity);
			}

			statusMessageResponse.setMessage("course have created successfully ");
			statusMessageResponse.setStatus("success");

			return statusMessageResponse;

		} else {

			statusMessageResponse.setMessage("course is not created");
			statusMessageResponse.setStatus("fail");

			return statusMessageResponse;

		}

	}

	private void addLecturesDay(AddCourseByDayId course) {

	}
}