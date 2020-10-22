package com.yoga.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yoga.api.entity.CourseEntity;
import com.yoga.api.entity.DayEntity;
import com.yoga.api.entity.LectureEntity;
import com.yoga.api.model.AllUserCourses;
import com.yoga.api.model.AllUserCoursesResponse;
import com.yoga.api.model.DaysResponse;
import com.yoga.api.model.Lecture;
import com.yoga.api.model.LectureByDay;
import com.yoga.api.repository.CourseRepository;
import com.yoga.api.repository.DayRepository;
import com.yoga.api.repository.LectureRepository;

@Service
public class GetCourseService {

	@Autowired
	CourseRepository courseRepository;

	@Autowired
	DayRepository dayRepository;
	
	@Autowired
	LectureRepository lecRepository;

	List<CourseEntity> courseEntityList;
	
	CourseEntity courseEntity;

	List<LectureEntity> lecEntityList;
	List<DayEntity> dayEntityList;
	LectureEntity lecEntity;
	Lecture lecture;	
	LectureByDay lectureByDay;
	List<LectureByDay> lectureByDayList = new ArrayList<>();

	DayEntity dayEntity;

	// Get course
	public AllUserCoursesResponse courses() {

		AllUserCoursesResponse allUserCoursesResponse = new AllUserCoursesResponse();

		List<AllUserCourses> allUserCoursesList = new ArrayList<>();

		courseEntityList = courseRepository.getAllCourses();

		if (!Objects.isNull(courseEntityList)) {

			AllUserCourses allUserCourses;

			for (CourseEntity courseEntity : courseEntityList) {

				// courseEntity = new CourseEntity();

				allUserCourses = new AllUserCourses();
				allUserCourses.setCourseName(courseEntity.getCourseName());
				allUserCourses.setStartDate(courseEntity.getStartDate());
				allUserCourses.setCourseId(courseEntity.getCourseId());
				allUserCourses.setCouseDurations(courseEntity.getCouseDuration());

				allUserCoursesList.add(allUserCourses);

			}

			allUserCoursesResponse.setCourses(allUserCoursesList);

		}

		return allUserCoursesResponse;
	}


	// Get All Course Entity By course ID
	public CourseEntity coursesByCourseId(Integer courseId) {
		courseEntity = courseRepository.getCourseEntityByCourseId(courseId);
		return courseEntity;
	}

}
