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
import com.yoga.api.entity.UserAccountEntity;
import com.yoga.api.model.AllCourses;
import com.yoga.api.model.AllCoursesResponse;
import com.yoga.api.model.AllUserCourses;
import com.yoga.api.model.AllUserCoursesResponse;
import com.yoga.api.model.Lecture;
import com.yoga.api.model.LectureByDay;
import com.yoga.api.repository.CourseRepository;
import com.yoga.api.repository.DayRepository;
import com.yoga.api.repository.LectureRepository;
import com.yoga.api.repository.UserAccountRepository;
import com.yoga.api.util.CompareDates;

@Service
public class GetCourseFromAdminService {

	@Autowired
	CourseRepository courseRepository;



	List<CourseEntity> courseEntityList;
	CourseEntity courseEntity;


	AllUserCourses allUserCourses;

	AllUserCoursesResponse allUserCoursesResponse;

	List<AllUserCourses> allUserCoursesList;

	CompareDates compareDates = new CompareDates();

	// Gell all courses based on user

	public AllUserCoursesResponse coursesForAdmin() throws ParseException {

		allUserCoursesResponse = new AllUserCoursesResponse();

		try {
			courseEntityList = courseRepository.findAll();
		} catch (Exception e) {
			return errorResponse();
		}

		if (Objects.isNull(courseEntityList)) {
			return errorResponse();
		}

		allUserCoursesList = new ArrayList<>();

		for (CourseEntity courseEntity : courseEntityList) {
			
			String compareDate = compareDates.compareCourseStartDate(courseEntity.getStartDate());	
			
			if (	compareDate.equals(ApiConstants.TRUE)) {

				if (!Objects.isNull(courseEntity)) {

					allUserCourses = new AllUserCourses();

					
					allUserCourses.setCourseName(courseEntity.getCourseName().toUpperCase() );
					
					allUserCourses.setCourseDuration(courseEntity.getCouseDuration());
					allUserCourses.setEndDate(courseEntity.getEndDate());
					allUserCourses.setDays(courseEntity.getDayEntity().size());
					allUserCourses.setStartDate(courseEntity.getStartDate());
					allUserCourses.setCourseId(courseEntity.getCourseId());
					allUserCourses.setLanguage(courseEntity.getLanguage().toUpperCase());
					allUserCoursesList.add(allUserCourses);

				}
			}

		}

		if (!Objects.isNull(allUserCoursesList)) {
			allUserCoursesResponse.setCourses(allUserCoursesList);
		}
		return successResponse();

	}

	private AllUserCoursesResponse errorResponse() {
		allUserCoursesResponse.setStatus(ApiConstants.FAILURE);
		allUserCoursesResponse.setMessage("Course is not present");
		return allUserCoursesResponse;
	}

	private AllUserCoursesResponse successResponse() {
		allUserCoursesResponse.setStatus(ApiConstants.SUCCESS);
		allUserCoursesResponse.setMessage("Course is not present");
		allUserCoursesResponse.setCourses(allUserCoursesList);
		return allUserCoursesResponse;
	}

}
