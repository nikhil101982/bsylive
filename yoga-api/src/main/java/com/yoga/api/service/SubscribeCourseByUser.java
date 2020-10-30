package com.yoga.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yoga.api.constant.ApiConstants;
import com.yoga.api.entity.CourseEntity;
import com.yoga.api.entity.UserAccountEntity;
import com.yoga.api.model.StatusResponse;
import com.yoga.api.model.SubscribeCourses;
import com.yoga.api.repository.CourseRepository;
import com.yoga.api.repository.DayRepository;
import com.yoga.api.repository.LectureRepository;
import com.yoga.api.repository.UserAccountRepository;

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

	StatusResponse statusResponse;

	List<CourseEntity> courseEntityList;

	List<Integer> userAccountCourseIdList;

	public StatusResponse subscribeTheCourse(SubscribeCourses subscribeCourses, String userEmail) {

		statusResponse = new StatusResponse();

		if (Objects.isNull(userEmail) || Objects.isNull(subscribeCourses)) {
			return errorResponse(userEmail);

		}

		try {
			userAccountEntity = userAccountRepository.getUserAccountEntityByEmail(userEmail);
		} catch (Exception e) {
			return errorResponse(userEmail);

		}

		if (Objects.isNull(userAccountEntity)) {
			return errorResponse(userEmail);
		}

		userAccountCourseIdList = new ArrayList<>();

		for (Integer courseId : subscribeCourses.getCourseId()) {

			try {
				courseEntity = courseRepository.getCourseEntityByCourseId(courseId);
			} catch (Exception e) {
				return errorResponse(userEmail);
			}

			if (Objects.isNull(courseEntity)) {
				return errorResponse(userEmail);
			}

			userAccountCourseIdList.add(courseEntity.getCourseId());

		}

		if (Objects.isNull(userAccountCourseIdList)) {
			return errorResponse(userEmail);
		}

		try {
			userAccountRepository.save(userAccountEntity);
		} catch (Exception e) {
			return errorResponse(userEmail);

		}

		return successResponse(userEmail);

	}

	private StatusResponse errorResponse(String userEmail) {
		statusResponse.setStatus(ApiConstants.FAILURE);
		statusResponse.setMessage("not able to subscribe the course!");
		statusResponse.setUserEmail(userEmail);
		return statusResponse;
	}

	private StatusResponse successResponse(String userEmail) {
		statusResponse.setStatus(ApiConstants.SUCCESS);
		statusResponse.setMessage("courses subscribed!");
		statusResponse.setUserEmail(userEmail);
		return statusResponse;
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
