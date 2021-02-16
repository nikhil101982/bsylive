package com.yoga.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yoga.api.entity.CourseEntity;
import com.yoga.api.entity.UserAccountEntity;
import com.yoga.api.model.StatusMessageResponse;
import com.yoga.api.model.RegisterCourses;
import com.yoga.api.repository.CourseRepository;
import com.yoga.api.repository.DayRepository;
import com.yoga.api.repository.LectureRepository;
import com.yoga.api.repository.UserAccountRepository;
import com.yoga.api.util.UtilMethods;

@Service
public class SubscribeCourseByUser {

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

	String successResponseMessage = null;
	String failureResponseMessage = null;
	
	UtilMethods utilMethods = new UtilMethods();


	public StatusMessageResponse registerCourses(RegisterCourses subscribeCourses, String userEmail) {

		successResponseMessage = "courses subscribed !";
		failureResponseMessage = "not able to subscribe the course!";

		if (Objects.isNull(userEmail) || Objects.isNull(subscribeCourses)) {
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

		userAccountCourseIdList = new ArrayList<>();

		for (Integer courseId : subscribeCourses.getCourseId()) {

			try {
				courseEntity = courseRepository.getCourseEntityByCourseId(courseId);
			} catch (Exception e) {
				return utilMethods.errorResponse(failureResponseMessage);
			}

			if (Objects.isNull(courseEntity)) {
				return utilMethods.errorResponse(failureResponseMessage);
			}

			userAccountCourseIdList.add(courseEntity.getCourseId());

		}

		if (Objects.isNull(userAccountCourseIdList)) {
			return utilMethods.errorResponse(failureResponseMessage);
		}

		try {
			userAccountRepository.save(userAccountEntity);
		} catch (Exception e) {
			return utilMethods.errorResponse(failureResponseMessage);

		}

		return utilMethods.successResponse(successResponseMessage);

	}


	public void deleteData() {
		try {
			courseRepository.deleteAll();
			userAccountRepository.deleteAll();
			lectureEntityRepository.deleteAll();
			dayRepository.deleteAll();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
