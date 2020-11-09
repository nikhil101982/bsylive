package com.yoga.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.mail.MessagingException;

import org.hibernate.mapping.Array;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.yoga.api.constant.ApiConstants;
import com.yoga.api.entity.CourseEntity;
import com.yoga.api.entity.LectureEntity;
import com.yoga.api.entity.UserAccountEntity;
import com.yoga.api.model.AddCourse;
import com.yoga.api.model.AddCourseResponse;
import com.yoga.api.model.AddListOfCourse;
import com.yoga.api.model.CourseResources;
import com.yoga.api.model.Day;
import com.yoga.api.model.RemoveCourseRequest;
import com.yoga.api.model.StatusMessageResponse;
import com.yoga.api.model.UserAccountResponse;
import com.yoga.api.repository.CourseRepository;
import com.yoga.api.repository.LectureRepository;
import com.yoga.api.repository.UserAccountRepository;
import com.yoga.api.util.SendEmailUtil;
import com.yoga.api.util.UtilMethods;

@Service
public class AddCourseService {

	
	@Autowired
	CourseRepository courseRepository;

	@Autowired
	LectureRepository lectureRepository;
	
	@Value("${forgot.password.email.send.from}")
	private String forgotPasswordSendEmailFrom;

	CourseEntity courseEntity;

	LectureEntity lectureEntity = new LectureEntity();

	AddCourseResponse addCourseResponse;

	List<LectureEntity> lecEntityList = new ArrayList<>();

	UtilMethods utilMethods = new UtilMethods();

	@Autowired
	UserAccountRepository userAccountRepository;

	UserAccountEntity userAccountEntity;

	List<CourseEntity> courseEntityList;
	
	@Autowired
	SendEmailUtil sendEmailUtil;

	 final String successMessage = "Course removed! ";
	 final String failureMessage = "Course is not present! ";

	// Add Course api
	public AddCourseResponse addCourse(CourseResources course) {
		

		addCourseResponse = new AddCourseResponse();

		String status = null;

		if (Objects.isNull(course)) {
			return errorResponse();
		}

		try {
			courseEntity = courseRepository.getCourseByCourseNameAndStartDateAndCouseDuration(course.getCourseName(),
					course.getStartDate(), course.getDay().size());
		} catch (Exception e) {
			return errorResponse();
		}

		if (!Objects.isNull(courseEntity)) {
			return errorResponse();

		}

		try {
			addLectures(course);
		} catch (Exception e) {
			return errorResponse();
		}

		try {
			createCourse(course);
		} catch (Exception e) {
			return errorResponse();
		}

		return addCourseResponse(course, status);

	}

	private AddCourseResponse errorResponse() {
		addCourseResponse.setMessage("course not added !");
		addCourseResponse.setStatus(ApiConstants.FAILURE);
		return addCourseResponse;
	}

	private void createCourse(CourseResources course) {
		courseEntity = new CourseEntity();
		courseEntity.setCourseName(course.getCourseName().toUpperCase());
		courseEntity.setCouseDuration(course.getDay().size());
		courseEntity.setStartDate(course.getStartDate().toUpperCase());

		courseRepository.save(courseEntity);
	}

	private void addLectures(CourseResources course) {

		for (Day day : course.getDay()) {

			lectureEntity.setCurrDate(day.getCurrentDate().toUpperCase());
			lectureEntity.setDisableJoinBtn("false");
			lectureEntity.setEndTime(day.getEndTime().toUpperCase());
			lectureEntity.setLectureName(day.getLectureName().toUpperCase());
			lectureEntity.setStartTime(day.getStartTime().toUpperCase());
			lectureEntity.setVideoIframeDynamicLink(day.getVideoIframeDynamicLink());
			lectureEntity.setLiveIframeDynamicLink(day.getLiveIframeDynamicLink());

			lecEntityList.add(lectureEntity);
		}

		if (!Objects.isNull(lecEntityList)) {
			lectureRepository.saveAll(lecEntityList);
		}
	}

	private AddCourseResponse addCourseResponse(CourseResources course, String status) {
		addCourseResponse.setCourseName(course.getCourseName());
		addCourseResponse.setCouseDuration(course.getDay().size());
		addCourseResponse.setMessage("course added !");
		addCourseResponse.setStartDate(course.getStartDate());
		addCourseResponse.setStatus(ApiConstants.SUCCESS);
		return addCourseResponse;
	}

	public StatusMessageResponse removeCourse(Integer courseId) {
		


		if (Objects.isNull(courseId)) {
			return utilMethods.errorResponse(failureMessage);
		}

		courseEntityList = new ArrayList<>();

		try {
			courseEntity = courseRepository.getCourseEntityByCourseId(courseId);
		} catch (Exception e) {
			return utilMethods.errorResponse(failureMessage);
		}

		if (!Objects.isNull(courseEntity)) {
			return utilMethods.errorResponse(failureMessage);

		}

		
		try {
			courseRepository.delete(courseEntity);
			return utilMethods.successResponse(successMessage);

		} catch (Exception e) {
			return utilMethods.errorResponse(failureMessage);

		}

	}

	public StatusMessageResponse updateUserCourses(AddListOfCourse courses) throws MessagingException {

		List<AddCourse> day = new ArrayList<>();

		if (Objects.isNull(courses)) {
			return utilMethods.errorResponse(failureMessage);
		}

		try {
			userAccountEntity = userAccountRepository.getUserAccountEntityByEmail(courses.getUserEmail());
		} catch (Exception e) {
			return utilMethods.errorResponse(failureMessage);
		}

		if (Objects.isNull(userAccountEntity)) {
			return utilMethods.errorResponse(failureMessage);
		}
		for (AddCourse course : courses.getCourses()) {

			courseEntity = new CourseEntity();
			try {
				courseEntity = courseRepository.getCourseEntityByCourseId(course.getCourseId());
			} catch (Exception e) {
				return utilMethods.errorResponse(failureMessage);
			}

			if (Objects.isNull(courseEntity)) {
				return utilMethods.errorResponse(failureMessage);
			}

			courseEntityList.add(courseEntity);
		}

		if (Objects.isNull(courseEntityList)) {
			return utilMethods.errorResponse(failureMessage);
		}

		try {
			userAccountEntity.setCourseEntity(courseEntityList);

		} catch (Exception e) {
			return utilMethods.errorResponse(failureMessage);
		}
		
		String subject = "Courses updated";
		String text = "Courses updated successfully";

		
		try {
			return sendEmailUtil.sendEmail(userAccountEntity.getEmailId(), forgotPasswordSendEmailFrom, subject, text, "User courses updated! ", "User courses not updated! ");
		} catch (Exception e) {
			return utilMethods.errorResponse(failureMessage);
		}

	}

}
