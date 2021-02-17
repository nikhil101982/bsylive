package com.yoga.api.service;

import java.util.ArrayList;
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
import com.yoga.api.model.CourseResources;
import com.yoga.api.model.Day;
import com.yoga.api.model.DayByCourseId;
import com.yoga.api.model.LectureByDay;
import com.yoga.api.repository.CourseRepository;
import com.yoga.api.repository.DayRepository;
import com.yoga.api.repository.LectureRepository;
import com.yoga.api.repository.UserAccountRepository;
import com.yoga.api.util.SendEmailUtil;
import com.yoga.api.util.UtilMethods;

@Service
public class UpdateCourService {

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

	private void addLectures(CourseResources course) {

		for (Day day : course.getDay()) {

			lectureEntity.setCurrDate(day.getCurrentDate().toUpperCase());
			lectureEntity.setDisableJoinBtn("false");
			lectureEntity.setEndTime(day.getEndTime().toUpperCase());
			lectureEntity.setLectureName(day.getLectureName());
			lectureEntity.setStartTime(day.getStartTime().toUpperCase());
			lectureEntity.setVideoIframeDynamicLink(day.getVideoIframeDynamicLink());
			lectureEntity.setLiveIframeDynamicLink(day.getLiveIframeDynamicLink());

			lecEntityList.add(lectureEntity);
		}

		if (!Objects.isNull(lecEntityList)) {
			lectureRepository.saveAll(lecEntityList);
		}
	}

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

	private List<DayByCourseId> dayList(CourseEntity courseEntity) {

		dayByCourseIdList = new ArrayList<>();
		DayEntity dayEntity;
		dayEntityList = new ArrayList<>();
		lecEntityList = new ArrayList<>();

		if (courseEntity.getDayEntity().size() == 0) {

			for (int i = 1; i < courseEntity.getCouseDuration(); i++) {

				String dayName = "Day".concat(Integer.toString(i));

				dayEntity = new DayEntity();
				dayEntity.setDayName(dayName);
				dayEntityList.add(dayEntity);
				dayRepository.save(dayEntity);

				dayByCourseId = new DayByCourseId();
				dayByCourseId.setDayName(dayName);
				dayByCourseId.setDayId(dayEntity.getDayId());
				dayByCourseIdList.add(dayByCourseId);

			}

			try {
				// dayRepository.saveAll(dayEntityList);
			} catch (Exception e) {
				System.out.println("Not able to save day entity list ! ");
			}

		} else {

			for (DayEntity dayEntity1 : courseEntity.getDayEntity()) {

				lectureByDayList = new ArrayList<>();

				for (LectureEntity lectureEntity : dayEntity1.getLecEntity()) {

					lectureEntity = createLectureEntityList(lectureEntity);
					lecEntityList.add(lectureEntity);

					lectureByDay = createLectureByDay(lectureEntity);
					lectureByDayList.add(lectureByDay);

				}

				dayByCourseId = new DayByCourseId();

				dayByCourseId.setDayName(dayEntity1.getDayName());
				dayByCourseId.setDayId(dayEntity1.getDayId());
				dayByCourseId.setLecture(lectureByDayList);
				dayByCourseIdList.add(dayByCourseId);

				dayEntity = new DayEntity();
				dayEntity.setDayId(dayEntity1.getDayId());
				dayEntity.setDayName(dayEntity1.getDayName());
				dayEntity.setLecEntity(lecEntityList);

				if (!Objects.isNull(lecEntityList)) {
					lectureRepository.saveAll(lecEntityList);
					dayEntity.setLecEntity(lecEntityList);

				}

				dayEntityList.add(dayEntity);

				try {
					dayRepository.saveAll(dayEntityList);
				} catch (Exception e) {
					System.out.println("Not able to save day entity list ! ");
				}

			}

		}

		try {

			courseEntity.setDayEntity(dayEntityList);
			courseRepository.save(courseEntity);
		} catch (Exception e) {
		}

		return dayByCourseIdList;

	}

	private LectureByDay createLectureByDay(LectureEntity lectureEntity2) {

		lectureByDay = new LectureByDay();

		if (!Objects.isNull(lectureEntity2)) {

			iframeLectureByDay(lectureByDay, lectureEntity2);
			lectureByDay.setSNo(lectureEntity2.getSNo());
			lectureByDay.setCurrentDate(lectureEntity2.getCurrDate());
			lectureByDay.setEndTime(lectureEntity2.getEndTime().toUpperCase());
			lectureByDay.setLectureName(lectureEntity2.getLectureName());
			lectureByDay.setStartTime(lectureEntity2.getStartTime().toUpperCase());
		}
		return lectureByDay;
	}

	private void iframeLectureByDay(LectureByDay lectureByDay2, LectureEntity lectureEntity2) {

		if (!Objects.isNull(lectureEntity.getVideoIframeDynamicLink())) {
			lectureByDay2.setVideoIframeDynamicLink(lectureEntity2.getVideoIframeDynamicLink());
			lectureByDay2.setDisableJoinBtn("false");
			lectureByDay2.setLiveIframeDynamicLink(null);

		} else {
			lectureByDay2.setVideoIframeDynamicLink(null);
			lectureByDay2.setDisableJoinBtn("false");
			lectureByDay2.setLiveIframeDynamicLink(lectureEntity2.getLiveIframeDynamicLink());

		}

	}

	private LectureEntity createLectureEntityList(LectureEntity lectureEntity) {

		newLectureEntity = new LectureEntity();
		if (!Objects.isNull(lectureEntity)) {

			iframe(lectureEntity, newLectureEntity);
			newLectureEntity.setSNo(lectureEntity.getSNo());
			newLectureEntity.setCurrDate(lectureEntity.getCurrDate());
			newLectureEntity.setEndTime(lectureEntity.getEndTime().toUpperCase());
			newLectureEntity.setLectureName(lectureEntity.getLectureName());
			newLectureEntity.setStartTime(lectureEntity.getStartTime().toUpperCase());
		}
		return newLectureEntity;
	}

	private AddCourseByDayId updateCourseSuccessResponse(CourseEntity courseEntity) {

		addCourseByDayId = new AddCourseByDayId();

		addCourseByDayId.setCourseName(courseEntity.getCourseName());
		addCourseByDayId.setCourseId(courseEntity.getCourseId());
		addCourseByDayId.setDuration(courseEntity.getCouseDuration());
		addCourseByDayId.setLanguage(courseEntity.getLanguage());
		addCourseByDayId.setEndDate(courseEntity.getEndDate());
		addCourseByDayId.setStartDate(courseEntity.getStartDate());
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

	private void iframe(LectureEntity LectByDay, LectureEntity lectureEntity) {

		lectureEntity = new LectureEntity();

		if (Objects.isNull(LectByDay.getVideoIframeDynamicLink())
				&& Objects.isNull(LectByDay.getLiveIframeDynamicLink())) {
			lectureEntity.setDisableJoinBtn("false");
			lectureEntity.setLiveIframeDynamicLink(null);
			lectureEntity.setVideoIframeDynamicLink(null);

		} else if (!Objects.isNull(LectByDay.getVideoIframeDynamicLink())
				&& !Objects.isNull(LectByDay.getLiveIframeDynamicLink())) {

			lectureEntity.setDisableJoinBtn("false");
			lectureEntity.setLiveIframeDynamicLink(LectByDay.getLiveIframeDynamicLink());
			lectureEntity.setVideoIframeDynamicLink(LectByDay.getVideoIframeDynamicLink());

		} else {

			if (!Objects.isNull(lectureEntity.getVideoIframeDynamicLink())) {
				lectureEntity.setVideoIframeDynamicLink(LectByDay.getVideoIframeDynamicLink());
				lectureEntity.setDisableJoinBtn("false");
				lectureEntity.setLiveIframeDynamicLink(null);

			} else {
				lectureEntity.setVideoIframeDynamicLink(null);
				lectureEntity.setDisableJoinBtn("false");
				lectureEntity.setLiveIframeDynamicLink(LectByDay.getLiveIframeDynamicLink());

			}

		}

	}

}
