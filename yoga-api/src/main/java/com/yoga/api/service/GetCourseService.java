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
import com.yoga.api.model.AllUserCourses;
import com.yoga.api.model.AllUserCoursesResponse;
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

	AllUserCoursesResponse allUserCoursesResponse;

	DayEntity dayEntity;

	// Get course
	public AllUserCoursesResponse courses() {

		allUserCoursesResponse = new AllUserCoursesResponse();

		List<AllUserCourses> allUserCoursesList = new ArrayList<>();

		try {
			courseEntityList = courseRepository.getAllCourses();
		} catch (Exception e) {
			return errorResponse();

		}

		if (Objects.isNull(courseEntityList)) {
			return errorResponse();
		}

		AllUserCourses allUserCourses;

		for (CourseEntity courseEntity : courseEntityList) {

			allUserCourses = new AllUserCourses();

			allUserCourses = new AllUserCourses();
			allUserCourses.setCourseName(courseEntity.getCourseName());
			allUserCourses.setStartDate(courseEntity.getStartDate());
			allUserCourses.setCourseId(courseEntity.getCourseId());
			allUserCourses.setCouseDurations(courseEntity.getDayEntity().size());
			allUserCoursesList.add(allUserCourses);

		}

		if ((Objects.isNull(allUserCoursesList))) {
			return errorResponse();
		}

		return successResponse(allUserCoursesList);
	}

	// Get All Course Entity By course ID
	public CourseEntity coursesByCourseId(Integer courseId) {
		courseEntity = courseRepository.getCourseEntityByCourseId(courseId);
		return courseEntity;
	}

	private AllUserCoursesResponse errorResponse() {

		allUserCoursesResponse.setStatus(ApiConstants.FAILURE);
		allUserCoursesResponse.setMessage("course is not available");
		allUserCoursesResponse.setCourses(null);
		return allUserCoursesResponse;
	}

	private AllUserCoursesResponse successResponse(List<AllUserCourses> allUserCoursesList) {

		allUserCoursesResponse.setStatus(ApiConstants.SUCCESS);
		allUserCoursesResponse.setMessage("course is available ");
		allUserCoursesResponse.setCourses(allUserCoursesList);
		return allUserCoursesResponse;
	}

}
