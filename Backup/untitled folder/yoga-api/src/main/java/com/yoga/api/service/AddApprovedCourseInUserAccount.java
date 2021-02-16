package com.yoga.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.yoga.api.entity.CourseEntity;
import com.yoga.api.entity.UserAccountEntity;
import com.yoga.api.model.AllUserCourses;
import com.yoga.api.model.CourseRegistrationEmailRequest;
import com.yoga.api.model.CourseRegistrationRequest;
import com.yoga.api.model.StatusMessageResponse;
import com.yoga.api.repository.CourseRepository;
import com.yoga.api.repository.DayRepository;
import com.yoga.api.repository.LectureRepository;
import com.yoga.api.repository.UserAccountRepository;
import com.yoga.api.util.SendEmailUtil;
import com.yoga.api.util.UtilMethods;

@Service
public class AddApprovedCourseInUserAccount {

	@Autowired
	CourseRepository courseRepository;

	@Autowired
	UserAccountRepository userAccountRepository;

	@Autowired
	LectureRepository lectureEntityRepository;

	@Autowired
	DayRepository dayRepository;

	CourseEntity courseEntity;

	UserAccountEntity userAccountEntity;

	List<CourseEntity> courseEntityList;

	List<Integer> userAccountCourseIdList;

	AllUserCourses allUserCourses;

	List<AllUserCourses> allUserCoursesList;

	CourseRegistrationEmailRequest courseRegistrationEmailRequest;

	//@Value("${course.registration.email.to}")
	private String toEmailId;

	String successResponseMessage = null;
	String failureResponseMessage = null;

	UtilMethods utilMethods = new UtilMethods();

	SendEmailUtil sendEmail = new SendEmailUtil();

	public StatusMessageResponse addCoursesInUser(CourseRegistrationRequest courseRegistrationRequest) {

		successResponseMessage = "Added courses successfully !";
		failureResponseMessage = "unable to add the courses!";

		if (Objects.isNull(courseRegistrationRequest)) {
			return utilMethods.errorResponse(failureResponseMessage);

		}

		try {
			userAccountEntity = userAccountRepository
					.getUserAccountEntityByEmail(courseRegistrationRequest.getUserEmail());
		} catch (Exception e) {
			return utilMethods.errorResponse(failureResponseMessage);

		}

		if (Objects.isNull(userAccountEntity)) {
			return utilMethods.errorResponse(failureResponseMessage);
		}

		// userAccountCourseIdList = new ArrayList<>();

		allUserCoursesList = new ArrayList<>();

		for (Integer courseId : courseRegistrationRequest.getCourseId()) {

			try {
				courseEntity = courseRepository.getCourseEntityByCourseId(courseId);
				courseEntityList.add(courseEntity);

			} catch (Exception e) {
				return utilMethods.errorResponse(failureResponseMessage);
			}

			if (Objects.isNull(courseEntity)) {
				return utilMethods.errorResponse(failureResponseMessage);
			}

		}

		if (Objects.isNull(courseEntityList)) {
			return utilMethods.errorResponse(failureResponseMessage);
		}

		userAccountEntity.setCourseEntity(courseEntityList);

		try {
			userAccountRepository.save(userAccountEntity);
		} catch (Exception e) {
			return utilMethods.errorResponse(failureResponseMessage);

		}

		return utilMethods.successResponse(successResponseMessage);

	}

	public StatusMessageResponse removeUser(String userEmail) {

		successResponseMessage = "Removed user !";
		failureResponseMessage = "unable to remove user! ";

		if (Objects.isNull(userEmail)) {
			return utilMethods.errorResponse(failureResponseMessage);

		}

		try {
			userAccountEntity = userAccountRepository.getUserAccountEntityByEmail(userEmail);
		} catch (Exception e) {
			return utilMethods.errorResponse(failureResponseMessage);

		}

		if (Objects.isNull(userAccountEntity)) {
			return utilMethods.errorResponse(failureResponseMessage);
		}

		try {
			userAccountRepository.delete(userAccountEntity);
		} catch (Exception e) {
			return utilMethods.errorResponse(failureResponseMessage);
		}

		return utilMethods.successResponse(successResponseMessage);
	}

}
