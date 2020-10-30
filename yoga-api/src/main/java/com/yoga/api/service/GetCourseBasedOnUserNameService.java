package com.yoga.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yoga.api.constant.ApiConstants;
import com.yoga.api.entity.CourseEntity;
import com.yoga.api.entity.UserAccountEntity;
import com.yoga.api.model.AllUserCourses;
import com.yoga.api.model.AllUserCoursesResponse;
import com.yoga.api.repository.CourseRepository;
import com.yoga.api.repository.UserAccountRepository;

@Service
public class GetCourseBasedOnUserNameService {

	@Autowired
	CourseRepository courseRepository;

	@Autowired
	UserAccountRepository userAccountRepository;

	List<CourseEntity> courseEntityList;
	CourseEntity courseEntity;

	UserAccountEntity userAccountEntity;

	AllUserCourses allUserCourses;

	AllUserCoursesResponse allUserCoursesResponse;

	List<AllUserCourses> allUserCoursesList;

	// Gell all courses based on user

	public AllUserCoursesResponse coursesByUserName(String userName) {

		allUserCoursesResponse = new AllUserCoursesResponse();

		try {
			userAccountEntity = userAccountRepository.findByUserName(userName);
		} catch (Exception e) {
			return errorResponse();
		}

		if (Objects.isNull(userAccountEntity)) {
			return errorResponse();
		}

		allUserCoursesList = new ArrayList<>();

		for (CourseEntity courseEntity : userAccountEntity.getCourseEntity()) {

			if (!Objects.isNull(courseEntity)) {

				allUserCourses = new AllUserCourses();

				allUserCourses.setCouseDurations(courseEntity.getDayEntity().size());
				allUserCourses.setCourseName(courseEntity.getCourseName());
				allUserCourses.setStartDate(courseEntity.getStartDate());
				allUserCourses.setCourseId(courseEntity.getCourseId());
				allUserCoursesList.add(allUserCourses);

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
