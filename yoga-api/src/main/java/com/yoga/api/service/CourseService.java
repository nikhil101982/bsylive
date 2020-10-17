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

	// Add Course api
	public StatusMessageResponse addCourse(AddCourseByDayId course) {

		courseEntity = courseRepository.getCourseByCourseNameAndStartDateAndCouseDuration(course.getCourseName(),
				course.getStartDate(), course.getCouseDuration());

		if (Objects.isNull(courseEntity)) {

			addLecturesDay(course);

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

		lecEntityList = new ArrayList<>();

		dayEntityList = new ArrayList<>();

		for (DayByCourseId day : course.getDay()) {

			//int number = 1;

			for (LectureByDay LectByDay : day.getLecture()) {

				lectureEntity = new LecEntity();

				lectureEntity.setCurrDate(LectByDay.getCurrentDate().toUpperCase());
				lectureEntity.setDisableJoinBtn(false);
				lectureEntity.setEndTime(LectByDay.getEndTime().toUpperCase());
				lectureEntity.setLectureName(LectByDay.getLectureName().toUpperCase());
				lectureEntity.setStartTime(LectByDay.getStartTime().toUpperCase());
				lectureEntity.setIFrameDynamicLink(LectByDay.getIFrameDynamicLink());
				lecEntityList.add(lectureEntity);

			}
			
			
			
			dayEntity = new DayEntity();
			//dayEntity.setDayName("Day " + number);
			dayEntity.setLecEntity(lecEntityList);
			dayEntity.setDayName(day.getDayName());

			dayEntityList.add(dayEntity);
			//number++;

		}

		if (!Objects.isNull(lecEntityList)) {

			lectureRepository.saveAll(lecEntityList);

		}

		if (!Objects.isNull(dayEntityList)) {

			dayRepository.saveAll(dayEntityList);

		}

	}
}