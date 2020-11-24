package com.yoga.api.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yoga.api.constant.ApiConstants;
import com.yoga.api.entity.CourseEntity;
import com.yoga.api.entity.DayEntity;
import com.yoga.api.entity.LectureEntity;
import com.yoga.api.model.AllCourses;
import com.yoga.api.model.AllCoursesResponse;
import com.yoga.api.model.AllUserCourses;
import com.yoga.api.model.AllUserCoursesResponse;
import com.yoga.api.model.Lecture;
import com.yoga.api.model.LectureByDay;
import com.yoga.api.repository.CourseRepository;
import com.yoga.api.repository.DayRepository;
import com.yoga.api.repository.LectureRepository;
import com.yoga.api.util.CompareDates;

@Service
public class TestGetCourseService {

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

	AllCoursesResponse allUserCoursesResponse;

	DayEntity dayEntity;

	CompareDates compareDates = new CompareDates();

	List<AllCourses> allUserCoursesList;

	// Get course
	public AllCoursesResponse courses() throws ParseException {

		allUserCoursesResponse = new AllCoursesResponse();

		allUserCoursesList = new ArrayList<>();

		try {
			courseEntityList = courseRepository.findAll();
		} catch (Exception e) {
			return errorResponse("eoor");

		}

		if (Objects.isNull(courseEntityList)) {
			return errorResponse("error");
		}

		AllCourses allUserCourses;

		for (CourseEntity courseEntity : courseEntityList) {

			String compareDate = compareDates.compareCourseStartDate(courseEntity.getStartDate());
			
			
			if (	compareDate.equals(ApiConstants.TRUE)) {


				allUserCourses = new AllCourses();
				allUserCourses.setCourseName(courseEntity.getCourseName());
				allUserCourses.setCourseId(courseEntity.getCourseId());
				allUserCoursesList.add(allUserCourses);

			}

		}

		if ((Objects.isNull(allUserCoursesList))) {
			return errorResponse("error");
		}

		if (allUserCoursesList.size() == 0) {
			return errorResponse("There is not approved cours!");
		}

		return successResponse(allUserCoursesList);
	}

	// Get All Course Entity By course ID
	public CourseEntity coursesByCourseId(Integer courseId) throws ParseException {

		courseEntity = courseRepository.getCourseEntityByCourseId(courseId);

		
		String compareDate = compareDates.compareCourseStartDate(courseEntity.getStartDate());
		
		
		if (	compareDate.equals(ApiConstants.TRUE)) {
			return null;
		}

		return courseEntity;
	}

	private AllCoursesResponse errorResponse(String message) {
		allUserCoursesResponse.setStatus(ApiConstants.FAILURE);
		allUserCoursesResponse.setMessage(message);
		allUserCoursesResponse.setCourses(null);
		return allUserCoursesResponse;
	}

	private AllCoursesResponse successResponse(List<AllCourses> allUserCoursesList) {

		allUserCoursesResponse.setStatus(ApiConstants.SUCCESS);
		allUserCoursesResponse.setMessage("");
		allUserCoursesResponse.setCourses(allUserCoursesList);
		return allUserCoursesResponse;
	}

}
