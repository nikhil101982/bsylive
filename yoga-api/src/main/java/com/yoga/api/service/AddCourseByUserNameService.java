package com.yoga.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yoga.api.entity.CourseEntity;
import com.yoga.api.entity.UserAccountEntity;
import com.yoga.api.model.StatusResponse;
import com.yoga.api.model.SubscribeCourses;
import com.yoga.api.repository.CourseRepository;
import com.yoga.api.repository.LecRepository;
import com.yoga.api.repository.UserAccountRepository;

@Service
public class AddCourseByUserNameService {

	@Autowired
	CourseRepository courseRepository;

	@Autowired
	UserAccountRepository userAccountRepository;

	@Autowired
	LecRepository lectureEntityRepository;

	CourseEntity courseEntity;

	UserAccountEntity userAccountEntity;

	StatusResponse statusResponse;

	List<CourseEntity> courseEntityList;

	List<Integer> userAccountCourseIdList;

	public StatusResponse subscribeTheCourse(SubscribeCourses subscribeCourses, String userEmail) {

		userAccountEntity = userAccountRepository.getUserAccountEntityByEmail(userEmail);
		userAccountCourseIdList = new ArrayList<>();
		
		statusResponse = new StatusResponse();

		if (!Objects.isNull(userAccountEntity)) {

			for (Integer courseId : subscribeCourses.getCourseId()) {
				courseEntity = courseRepository.getCourseEntityByCourseId(courseId);

				if (!Objects.isNull(courseEntity)) {
					userAccountCourseIdList.add(courseEntity.getCourseId());
				}

			}

		}

		if (!Objects.isNull(userAccountCourseIdList)) {
			userAccountEntity.setCourseId(userAccountCourseIdList);
			userAccountRepository.save(userAccountEntity);
			statusResponse.setStatus("success");

		} else {
			statusResponse.setStatus("failure");

		}

		return statusResponse;

	}

	public void deleteData() {
		courseRepository.deleteAll();
		userAccountRepository.deleteAll();
		lectureEntityRepository.deleteAll();

	}

}
