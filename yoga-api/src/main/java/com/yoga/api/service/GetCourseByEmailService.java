package com.yoga.api.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.yoga.api.constant.ApiConstants;
import com.yoga.api.entity.CourseEntity;
import com.yoga.api.entity.UserAccountEntity;
import com.yoga.api.model.AllUserCourses;
import com.yoga.api.model.AllUserCoursesResponse;
import com.yoga.api.repository.CourseRepository;
import com.yoga.api.repository.UserAccountRepository;
import com.yoga.api.util.CompareDates;

@Service
public class GetCourseByEmailService {

	@Autowired
	CourseRepository courseRepository;

	@Autowired
	GetCourseFromAdminService getCourseFromAdminService;

	@Autowired
	UserAccountRepository userAccountRepository;

	List<CourseEntity> courseEntityList;
	CourseEntity courseEntity;

	UserAccountEntity userAccountEntity;

	AllUserCourses allUserCourses;

	AllUserCoursesResponse allUserCoursesResponse;

	List<AllUserCourses> allUserCoursesList;

	CompareDates compareDates = new CompareDates();
	
<<<<<<< HEAD
	@Value("${admin.authentication}")
	private boolean authenticationFlag;
=======
	@Value("${authentication.flag}")
	private String adminUserAuthFlag;
>>>>>>> 64d8723fc67e84e6b35f2a44fbfcf37bd22d5a48

	// Gell all courses based on user

	public AllUserCoursesResponse coursesByUserName(String userEmail, String userRole) throws ParseException {

		 String flag = "false";

		allUserCoursesResponse = new AllUserCoursesResponse();

		if (Objects.isNull(userRole) && Objects.isNull(userEmail)) {
			return errorResponse();
		}

		try {
			userAccountEntity = userAccountRepository.findByUserEmail(userEmail);
		} catch (Exception e) {
			return errorResponse();
		}
<<<<<<< HEAD
=======

		if (adminUserAuthFlag.equals("false")) {
			return getCourseFromAdminService.coursesForAdmin();
		}
>>>>>>> 64d8723fc67e84e6b35f2a44fbfcf37bd22d5a48
		
		if (userAccountEntity.getRole().equals(ApiConstants.ADMIN_ROLE)) {
			return getCourseFromAdminService.coursesForAdmin();
		}
		
		if(!authenticationFlag) {
			return getCourseFromAdminService.coursesForAdmin();
		}

		if (Objects.isNull(userAccountEntity)) {
			return errorResponse();
		}

		allUserCoursesList = new ArrayList<>();

		for (CourseEntity courseEntity : userAccountEntity.getCourseEntity()) {

			String compareDate = compareDates.compareCourseStartDate(courseEntity.getStartDate());

			if (compareDate.equals(ApiConstants.TRUE)) {

				if (!Objects.isNull(courseEntity)) {

					allUserCourses = new AllUserCourses();
					allUserCourses.setEndDate(courseEntity.getEndDate());
					
					allUserCourses.setCourseName(courseEntity.getCourseName().toUpperCase() );

					allUserCourses.setDays(courseEntity.getDayEntity().size());
					allUserCourses.setStartDate(courseEntity.getStartDate());
					allUserCourses.setCourseId(courseEntity.getCourseId());
					System.out.println(courseEntity.getLanguage().toUpperCase());
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
