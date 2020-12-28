package com.yoga.api.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.yoga.api.constant.ApiConstants;
import com.yoga.api.entity.CourseEntity;
import com.yoga.api.entity.DayEntity;
import com.yoga.api.entity.LectureEntity;
import com.yoga.api.entity.UserAccountEntity;
import com.yoga.api.model.AddCourseByDayId;
import com.yoga.api.model.AddCourseResponse;
import com.yoga.api.model.DayByCourseId;
import com.yoga.api.model.LectureByDay;
import com.yoga.api.repository.CourseRepository;
import com.yoga.api.repository.DayRepository;
import com.yoga.api.repository.LectureRepository;
import com.yoga.api.repository.UserAccountRepository;
import com.yoga.api.util.SendEmailUtil;
import com.yoga.api.util.UtilMethods;

@Service
public class UpdateCourTempService {

	@Autowired
	CourseRepository courseRepository;

	@Autowired
	LectureRepository lectureRepository;

	@Autowired
	DayRepository dayRepository;

	@Autowired
	UserAccountRepository userAccountRepository;

	@Value("${forgot.password.email.send.from}")
	private String forgotPasswordSendEmailFrom;

	CourseEntity courseEntity;

	LectureEntity lectureEntity = new LectureEntity();

	AddCourseResponse addCourseResponse;

	List<LectureEntity> lecEntityList;

	UtilMethods utilMethods = new UtilMethods();

	UserAccountEntity userAccountEntity;

	List<CourseEntity> courseEntityList;

	List<DayByCourseId> dayByCourseIdList;

	AddCourseByDayId addCourseByDayId;
	
	DayByCourseId dayByCourseId;

	List<DayEntity> dayEntityList;

	DayEntity dayEntity;

	LectureByDay lectureByDay;

	LectureEntity newLectureEntity;

	List<LectureByDay> lectureByDayList;

	@Autowired
	SendEmailUtil sendEmailUtil;

	String successMessage = "Course removed! ";
	String failureMessage = "Course is not present! ";

	List<DayByCourseId> day = new ArrayList<>();

//	private void addLectures(CourseResources course) {
//
//		for (Day day : course.getDay()) {
//
//			lectureEntity.setCurrDate(day.getCurrentDate().toUpperCase());
//			lectureEntity.setDisableJoinBtn("false");
//			lectureEntity.setEndTime(day.getEndTime().toUpperCase());
//			lectureEntity.setLectureName(day.getLectureName().toUpperCase());
//			lectureEntity.setStartTime(day.getStartTime().toUpperCase());
//			lectureEntity.setVideoIframeDynamicLink(day.getVideoIframeDynamicLink());
//			lectureEntity.setLiveIframeDynamicLink(day.getLiveIframeDynamicLink());
//
//			lecEntityList.add(lectureEntity);
//		}
//
//		if (!Objects.isNull(lecEntityList)) {
//			lectureRepository.saveAll(lecEntityList);
//		}
//	}

	public AddCourseByDayId updateCourse(Integer courseId) {

		if (Objects.isNull(courseId)) {
			return updateCourseFailureResponse();
		}

		try {
			courseEntity = courseRepository.getCourseEntityByCourseId(courseId);
		} catch (Exception e) {
			return updateCourseFailureResponse();
		}

		if (Objects.isNull(courseEntity)) {
			return updateCourseFailureResponse();
		}

		return updateCourseSuccessResponse(courseEntity);

	}

	// list of days
	// each day have list of lecture.

	private List<DayByCourseId> dayList(CourseEntity courseEntity) {

		dayByCourseIdList = new ArrayList<>();
		dayEntityList = new ArrayList<>();
		lecEntityList = new ArrayList<>();

		int numberOfDays = courseEntity.getDayEntity().size();

		Integer[] arrayOfDayId = new Integer[numberOfDays];

		int count = 0;

		for (DayEntity dayEntity : courseEntity.getDayEntity()) {
			arrayOfDayId[count] = dayEntity.getDayId();
			count = count + 1;
		}

		Arrays.sort(arrayOfDayId);
				

		for (int i = 0; i < numberOfDays; i++) {
			
			
			DayEntity dayEntity1 = dayRepository.getDayEntityByDayId(arrayOfDayId[i]);


			dayByCourseId = new DayByCourseId();

			if (!Objects.isNull(dayEntity1.getLecEntity())) {

				lectureByDayList = new ArrayList<>();

				for (LectureEntity lectureEntity : dayEntity1.getLecEntity()) {
					lectureByDay = new LectureByDay();
					lectureByDay = createLectureEntityList(lectureByDay, lectureEntity);
					lectureByDayList.add(lectureByDay);
				}
			}

			dayByCourseId = new DayByCourseId();

			dayByCourseId.setDayName(dayEntity1.getDayName());
			dayByCourseId.setDayId(dayEntity1.getDayId());
			dayByCourseId.setLecture(lectureByDayList);
			dayByCourseIdList.add(dayByCourseId);
		}

		return dayByCourseIdList;

	}

//	private LectureByDay createLectureByDay(LectureEntity lectureEntity2) {
//
//		lectureByDay = new LectureByDay();
//
//		if (!Objects.isNull(lectureEntity2)) {
//
//			iframeLectureByDay(lectureByDay, lectureEntity2);
//			lectureByDay.setSNo(lectureEntity2.getSNo());
//			lectureByDay.setCurrentDate(lectureEntity2.getCurrDate());
//			lectureByDay.setEndTime(lectureEntity2.getEndTime().toUpperCase());
//			lectureByDay.setLectureName(lectureEntity2.getLectureName().toUpperCase());
//			lectureByDay.setStartTime(lectureEntity2.getStartTime().toUpperCase());
//		}
//		return lectureByDay;
//	}

//	private void iframeLectureByDay(LectureByDay lectureByDay2, LectureEntity lectureEntity2) {
//
//		if (Objects.isNull(lectureEntity2.getVideoIframeDynamicLink())
//				&& Objects.isNull(lectureEntity2.getLiveIframeDynamicLink())) {
//			lectureByDay2.setDisableJoinBtn("true");
//			lectureByDay2.setLiveIframeLink(null);
//			lectureByDay2.setVideoIframeLink(null);
//
//		} else if (!Objects.isNull(lectureEntity2.getVideoIframeDynamicLink())
//				&& !Objects.isNull(lectureEntity2.getLiveIframeDynamicLink())) {
//
//			lectureByDay2.setDisableJoinBtn("false");
//			lectureByDay2.setLiveIframeLink(lectureEntity2.getLiveIframeDynamicLink());
//			lectureByDay2.setVideoIframeLink(lectureEntity2.getVideoIframeDynamicLink());
//
//		} else {
//
//			if (!Objects.isNull(lectureEntity.getVideoIframeDynamicLink())) {
//				lectureByDay2.setVideoIframeLink(lectureEntity2.getVideoIframeDynamicLink());
//				lectureByDay2.setDisableJoinBtn("false");
//				lectureByDay2.setLiveIframeLink(null);
//
//			} else {
//				lectureByDay2.setVideoIframeLink(null);
//				lectureByDay2.setDisableJoinBtn("false");
//				lectureByDay2.setLiveIframeLink(lectureEntity2.getLiveIframeDynamicLink());
//
//			}
//
//		}
//
//	}

	private LectureByDay createLectureEntityList(LectureByDay lectureByDay, LectureEntity lectureEntity) {

		if (!Objects.isNull(lectureEntity)) {

			lectureByDay = getIframe(lectureByDay, lectureEntity);
			lectureByDay.setSNo(lectureEntity.getSNo());
			lectureByDay.setEndTime(lectureEntity.getEndTime().toUpperCase());
			lectureByDay.setLectureName(lectureEntity.getLectureName().toUpperCase());
			lectureByDay.setStartTime(lectureEntity.getStartTime().toUpperCase());
			lectureByDay.setLiveIframeDynamicLink(lectureEntity.getLiveIframeDynamicLink());
			lectureByDay.setVideoIframeDynamicLink(lectureEntity.getVideoIframeDynamicLink());
			
		}
		return lectureByDay;
	}

	private AddCourseByDayId updateCourseSuccessResponse(CourseEntity courseEntity) {

		addCourseByDayId = new AddCourseByDayId();

		addCourseByDayId.setCourseName(courseEntity.getCourseName());
		addCourseByDayId.setCourseId(courseEntity.getCourseId());
		addCourseByDayId.setDuration(courseEntity.getCouseDuration());
		addCourseByDayId.setLanguage(courseEntity.getLanguage());
		addCourseByDayId.setEndDate(courseEntity.getEndDate());
		addCourseByDayId.setStartDate(courseEntity.getStartDate());
		addCourseByDayId.setEndDate(courseEntity.getEndDate());
		addCourseByDayId.setMessage("Course updated! ");
		addCourseByDayId.setStatus(ApiConstants.SUCCESS);

		List<DayByCourseId> daysList = dayList(courseEntity);

		addCourseByDayId.setDay(daysList);

		return addCourseByDayId;
	}

	private AddCourseByDayId updateCourseFailureResponse() {

		addCourseByDayId = new AddCourseByDayId();

		failureMessage = "Not able to update course! ";
		addCourseByDayId.setMessage(failureMessage);
		addCourseByDayId.setStatus(ApiConstants.FAILURE);
		return addCourseByDayId;
	}

//	private void iframe(LectureEntity LectByDay, LectureEntity lectureEntity) {
//
//		lectureEntity = new LectureEntity();
//
//		if (Objects.isNull(LectByDay.getVideoIframeDynamicLink())
//				&& Objects.isNull(LectByDay.getLiveIframeDynamicLink())) {
//			lectureEntity.setDisableJoinBtn("true");
//			lectureEntity.setLiveIframeDynamicLink(null);
//			lectureEntity.setVideoIframeDynamicLink(null);
//
//		} else if (!Objects.isNull(LectByDay.getVideoIframeDynamicLink())
//				&& !Objects.isNull(LectByDay.getLiveIframeDynamicLink())) {
//
//			lectureEntity.setDisableJoinBtn("false");
//			lectureEntity.setLiveIframeDynamicLink(LectByDay.getLiveIframeDynamicLink());
//			lectureEntity.setVideoIframeDynamicLink(LectByDay.getVideoIframeDynamicLink());
//
//		} else {
//
//			if (!Objects.isNull(lectureEntity.getVideoIframeDynamicLink())) {
//				lectureEntity.setVideoIframeDynamicLink(LectByDay.getVideoIframeDynamicLink());
//				lectureEntity.setDisableJoinBtn("false");
//				lectureEntity.setLiveIframeDynamicLink(null);
//
//			} else {
//				lectureEntity.setVideoIframeDynamicLink(null);
//				lectureEntity.setDisableJoinBtn("false");
//				lectureEntity.setLiveIframeDynamicLink(LectByDay.getLiveIframeDynamicLink());
//
//			}
//
//		}
//
//	}

	private LectureByDay getIframe(LectureByDay lectureByDay, LectureEntity lectureEntity) {

		lectureEntity = new LectureEntity();

		if (Objects.isNull(lectureEntity.getVideoIframeDynamicLink())
				&& Objects.isNull(lectureEntity.getLiveIframeDynamicLink())) {
			lectureByDay.setDisableJoinBtn("true");
			lectureByDay.setLiveIframeDynamicLink(null);
			lectureByDay.setVideoIframeDynamicLink(null);

		} else if (!Objects.isNull(lectureEntity.getVideoIframeDynamicLink())
				&& !Objects.isNull(lectureEntity.getLiveIframeDynamicLink())) {

			lectureByDay.setDisableJoinBtn("false");
			lectureByDay.setLiveIframeDynamicLink(lectureEntity.getLiveIframeDynamicLink());
			lectureByDay.setVideoIframeDynamicLink(lectureEntity.getVideoIframeDynamicLink());

		} else {

			if (!Objects.isNull(lectureEntity.getVideoIframeDynamicLink())) {
				lectureByDay.setVideoIframeDynamicLink(lectureEntity.getVideoIframeDynamicLink());
				lectureByDay.setDisableJoinBtn("false");
				lectureByDay.setLiveIframeDynamicLink(null);

			} else {
				lectureByDay.setVideoIframeDynamicLink(null);
				lectureByDay.setDisableJoinBtn("false");
				lectureByDay.setLiveIframeDynamicLink(lectureEntity.getLiveIframeDynamicLink());

			}

		}
		return lectureByDay;

	}

}
