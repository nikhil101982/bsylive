package com.yoga.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yoga.api.entity.CourseEntity;
import com.yoga.api.entity.LecEntity;
import com.yoga.api.model.AddCourseResponse;
import com.yoga.api.model.CourseResources;
import com.yoga.api.model.Day;
import com.yoga.api.repository.CourseRepository;
import com.yoga.api.repository.LecRepository;

@Service
public class AddCourseService {

	@Autowired
	CourseRepository courseRepository;

	@Autowired
	LecRepository lectureRepository;

	CourseEntity courseEntity;

	LecEntity lectureEntity = new LecEntity();


	AddCourseResponse addCourseResponse;
	
	List<LecEntity> lecEntityList = new ArrayList<>();


	// Add Course api
	public AddCourseResponse addCourse(CourseResources course) {

		courseEntity = courseRepository.getCourseByCourseNameAndStartDate(course.getCourseName(),
				course.getStartDate());
		
		addCourseResponse = new AddCourseResponse();

		if (Objects.isNull(courseEntity)) {

			for (Day day: course.getDay()) {
				
				
				System.out.println("day.getCurrentDate().toUpperCase()  "+day.getCurrentDate().toUpperCase());

				lectureEntity.setCurrDate(day.getCurrentDate().toUpperCase());
				lectureEntity.setDisableJoinBtn(false);
				
				System.out.println("day.getEndTime().toUpperCase()  "+ day.getEndTime().toUpperCase());

				
				lectureEntity.setEndTime(day.getEndTime().toUpperCase());
				lectureEntity.setLectureName(day.getLectureName().toUpperCase());
				lectureEntity.setStartTime(day.getStartTime().toUpperCase());
				//lectureEntity.setLecId(lectureEntity.getLecId());
								
				lecEntityList.add(lectureEntity);
			}
		}


		if (!Objects.isNull(lecEntityList)) {
			lectureRepository.saveAll(lecEntityList);
		}

		courseEntity = new CourseEntity();

		courseEntity.setCourseName(course.getCourseName().toUpperCase());
		courseEntity.setCouseDuration(course.getCouseDuration().toUpperCase());
		courseEntity.setStartDate(course.getStartDate().toUpperCase());
		//courseEntity.setLecId(courseEntity.getLecId());

		if (!Objects.isNull(courseEntity)) {

			courseRepository.save(courseEntity);
			
			addCourseResponse.setCourseName(course.getCourseName());
			addCourseResponse.setCouseDuration(course.getCouseDuration());
			addCourseResponse.setMessage("Successfully added the course");
			addCourseResponse.setStartDate(course.getStartDate());

			return addCourseResponse;

		}

		addCourseResponse.setCourseName(course.getCourseName());
		addCourseResponse.setCouseDuration(course.getCouseDuration());
		addCourseResponse.setMessage("Not able to add the course");
		addCourseResponse.setStartDate(course.getStartDate());

		return addCourseResponse;
	}

}
