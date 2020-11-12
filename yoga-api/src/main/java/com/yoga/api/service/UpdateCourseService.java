package com.yoga.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.yoga.api.entity.CourseEntity;
import com.yoga.api.entity.LectureEntity;
import com.yoga.api.entity.UserAccountEntity;
import com.yoga.api.model.AddCourse;
import com.yoga.api.model.AddCourseResponse;
import com.yoga.api.model.AddListOfCourse;
import com.yoga.api.model.StatusMessageResponse;
import com.yoga.api.repository.CourseRepository;
import com.yoga.api.repository.LectureRepository;
import com.yoga.api.repository.UserAccountRepository;
import com.yoga.api.util.SendEmailUtil;
import com.yoga.api.util.UtilMethods;

@Service
public class UpdateCourseService {

	@Value("${forgot.password.email.send.from}")
	private String forgotPasswordSendEmailFrom;

	@Autowired
	CourseRepository courseRepository;

	CourseEntity courseEntity;

	List<CourseEntity> courseEntityList;

	@Autowired
	UserAccountRepository userAccountRepository;

	UserAccountEntity userAccountEntity;

	UtilMethods utilMethods = new UtilMethods();

	@Autowired
	SendEmailUtil sendEmailUtil;

	StatusMessageResponse statusResp;

	final String successMessage = "Course removed! ";
	final String failureMessage = "Course is not present! ";

	String subject = "Courses updated";
	String text = "Courses updated successfully";

	public StatusMessageResponse updateUserCourses(AddListOfCourse courses) throws MessagingException {

		statusResp = new StatusMessageResponse();

		if (Objects.isNull(courses)) {
			return utilMethods.errorResponse(failureMessage);
		}

		try {
			userAccountEntity = userAccountRepository.findByUserEmail(courses.getUserEmail());
		} catch (Exception e) {
			return utilMethods.errorResponse(failureMessage);
		}

		if (Objects.isNull(userAccountEntity)) {
			return utilMethods.errorResponse(failureMessage);
		}

		if (courses.getExistingCourses().isEmpty() && courses.getNewCourses().isEmpty()) {
			return courseEntityNull(subject, text, courses);
		}

		if (!courses.getExistingCourses().isEmpty() && !courses.getNewCourses().isEmpty()) {
			return courseEntityInUserAccountNullCourseUpdate(subject, text, courses);
		}

		if (!courses.getNewCourses().isEmpty()) {
			return courseEntityInUserAccountOneListNullCourseUpdate(subject, text, courses.getNewCourses());
		}

		if (!courses.getExistingCourses().isEmpty()) {
			return courseEntityInUserAccountOneListNullCourseUpdate(subject, text, courses.getExistingCourses());
		}
		return statusResp;

	}

	private StatusMessageResponse courseEntityNull(String subject2, String text2, AddListOfCourse courses) {

		courseEntityList = new ArrayList<>();

		try {

			userAccountEntity.setCourseEntity(courseEntityList);
			userAccountRepository.save(userAccountEntity);

		} catch (Exception e) {
			return utilMethods.errorResponse(failureMessage);
		}

		return sendEmail(subject, text);

	}

	private List<CourseEntity> courseEntityListObject(List<AddCourse> addCourseList) {

		courseEntityList = new ArrayList<>();

		for (AddCourse course : addCourseList) {

			courseEntity = new CourseEntity();

			if (!Objects.isNull(course)) {

				try {
					courseEntity = courseRepository.getCourseEntityByCourseId(course.getCourseId());
				} catch (Exception e) {
				}

			}

			courseEntityList.add(courseEntity);
		}
		return courseEntityList;
	}

	private StatusMessageResponse courseEntityInUserAccountNullCourseUpdate(String subject, String text,
			AddListOfCourse courses) {

		courseEntityList = courseEntityListObjectForBothNotNull(courses);

		try {

			userAccountEntity.setCourseEntity(courseEntityList);
			userAccountRepository.save(userAccountEntity);

		} catch (Exception e) {
			return utilMethods.errorResponse(failureMessage);
		}

		return sendEmail(subject, text);
	}

	private List<CourseEntity> courseEntityListObjectForBothNotNull(AddListOfCourse courses) {

		courseEntityList = new ArrayList<>();

		for (AddCourse course : courses.getExistingCourses()) {

			courseEntity = new CourseEntity();

			if (!Objects.isNull(course)) {
				courseEntity = courseRepository.getCourseEntityByCourseId(course.getCourseId());
			}

			courseEntityList.add(courseEntity);
		}

		for (AddCourse course : courses.getNewCourses()) {

			courseEntity = new CourseEntity();

			if (!Objects.isNull(course)) {
				courseEntity = courseRepository.getCourseEntityByCourseId(course.getCourseId());
			}

			courseEntityList.add(courseEntity);
		}

		return courseEntityList;

	}

	private StatusMessageResponse courseEntityInUserAccountOneListNullCourseUpdate(String subject, String text,
			List<AddCourse> newAddCourseList) {

		courseEntityList = courseEntityListObject(newAddCourseList);

		try {

			userAccountEntity.setCourseEntity(courseEntityList);
			userAccountRepository.save(userAccountEntity);

		} catch (Exception e) {
			return utilMethods.errorResponse(failureMessage);
		}

		return sendEmail(subject, text);
	}

	private StatusMessageResponse sendEmail(String subject, String text) {
		try {
			return sendEmailUtil.sendEmail(userAccountEntity.getUserEmail(), forgotPasswordSendEmailFrom, subject, text,
					"User courses updated! ", "User courses not updated! ");
		} catch (Exception e) {
			return utilMethods.errorResponse(failureMessage);
		}
	}

}
