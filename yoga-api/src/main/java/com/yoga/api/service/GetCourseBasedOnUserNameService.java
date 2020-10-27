package com.yoga.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

		userAccountEntity = userAccountRepository.findByUserName(userName);

		if (!Objects.isNull(userAccountEntity)) {

			allUserCoursesResponse = new AllUserCoursesResponse();
			
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
			return allUserCoursesResponse;

		} else {
			return allUserCoursesResponse;

		}

	}

}
