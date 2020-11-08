package com.yoga.api.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yoga.api.constant.ApiConstants;
import com.yoga.api.entity.CourseEntity;
import com.yoga.api.entity.DayEntity;
import com.yoga.api.entity.LectureEntity;
import com.yoga.api.model.DayByCourseId;
import com.yoga.api.model.Lecture;
import com.yoga.api.model.LectureByDay;
import com.yoga.api.repository.CourseRepository;
import com.yoga.api.repository.DayRepository;
import com.yoga.api.repository.LectureRepository;
import com.yoga.api.util.CompareDates;

@Service
public class GetCourseByAdminService {

	@Autowired
	CourseRepository courseRepository;

	@Autowired
	LectureRepository lecRepository;

	@Autowired
	DayRepository dayRepository;

	List<CourseEntity> courseEntityList;

	CourseEntity courseEntity;

	List<LectureEntity> lecEntityList;

	LectureEntity lecEntity;

	LectureEntity lecEntity2;

	Lecture lecture;

	LectureByDay lectureByDay;

	List<LectureByDay> lectureByDayList;

	List<DayEntity> dayEntityList;

	DayEntity dayEntity;

	DayByCourseId dayByCourseId = new DayByCourseId();
	
	CompareDates compareDates = new CompareDates();

	public DayByCourseId getCourseByAdmin(Integer courseId, Integer dayId) throws ParseException {

		if (Objects.isNull(courseId) || Objects.isNull(dayId)) {
			return errorResponse("error");
		}

		try {
			courseEntity = courseRepository.getCourseEntityByCourseId(courseId);
		} catch (Exception e) {
			return errorResponse("error");
		}
		
		if (Objects.isNull(courseEntity)) {
			return errorResponse("error");
		}

		
		if(!compareDates.compareCourseStartDate(courseEntity.getStartDate())) {
			return errorResponse("error");

		}
		

		dayEntityList = courseEntity.getDayEntity();

		lectureByDayList = new ArrayList<>();

		// Day
		try {
			dayEntity = dayRepository.getDayEntityByDayId(dayId);
		} catch (Exception e) {
			return errorResponse("error");
		}

		if (Objects.isNull(dayEntity)) {
			return errorResponse("error");
		}

		lecEntityList = dayEntity.getLecEntity();

		int numberOfLecture = dayEntity.getLecEntity().size();

		int[] arrayOfLectureId = new int[numberOfLecture];

		int count = 0;

		for (LectureEntity lectureEntity : lecEntityList) {

			arrayOfLectureId[count] = lectureEntity.getLectureId();
			count = count + 1;
		}

		Arrays.sort(arrayOfLectureId);

		for (int i = 0; i < numberOfLecture; i++) {

			try {
				lectureByDay = new LectureByDay();

				lectureByDay.setLectureByDayId(arrayOfLectureId[i]);

				
				LectureEntity lectureEntity2 = lecRepository.getLecEntityByLecId(arrayOfLectureId[i]);

				if (Objects.isNull(lectureEntity2.getVideoIframeDynamicLink())
						&& Objects.isNull(lectureEntity2.getLiveIframeDynamicLink())) {

					lectureByDay.setDisableJoinBtn(lectureEntity2.getDisableJoinBtn());
					lectureByDay.setVideoIframeDynamicLink("");
					lectureByDay.setLiveIframeDynamicLink("");

				}

				else if (!Objects.isNull(lectureEntity2.getVideoIframeDynamicLink())
						&& !Objects.isNull(lectureEntity2.getLiveIframeDynamicLink())) {

					lectureByDay.setVideoIframeDynamicLink(lectureEntity2.getVideoIframeDynamicLink());
					lectureByDay.setDisableJoinBtn(lectureEntity2.getDisableJoinBtn());
					lectureByDay.setLiveIframeDynamicLink(lectureEntity2.getLiveIframeDynamicLink());

				} else {

					if (!Objects.isNull(lectureEntity2.getVideoIframeDynamicLink())) {

						lectureByDay.setVideoIframeDynamicLink(lectureEntity2.getVideoIframeDynamicLink());
						lectureByDay.setDisableJoinBtn(lectureEntity2.getDisableJoinBtn());
						lectureByDay.setLiveIframeDynamicLink("");

					} else {

						lectureByDay.setLiveIframeDynamicLink(lectureEntity2.getLiveIframeDynamicLink());
						lectureByDay.setDisableJoinBtn(lectureEntity2.getDisableJoinBtn());
						lectureByDay.setVideoIframeDynamicLink("");

					}

				}
				lectureByDay.setSNo(lectureEntity2.getSNo());
				lectureByDay.setCurrentDate(lectureEntity2.getCurrDate());
				lectureByDay.setEndTime(lectureEntity2.getEndTime());
				lectureByDay.setLectureName(lectureEntity2.getLectureName());
				lectureByDay.setStartTime(lectureEntity2.getStartTime());

				lectureByDayList.add(lectureByDay);
			} catch (Exception e) {
				return errorResponse("error");
			}

		}

		DayByCourseId dayByCourseId = successResponse();

		if (Objects.isNull(dayByCourseId)) {
			return errorResponse("Course is not present !");

		}
		return dayByCourseId;

	}

	private DayByCourseId successResponse() {

		dayByCourseId.setDayName(dayEntity.getDayName());
		dayByCourseId.setDayId(dayEntity.getDayId());
		dayByCourseId.setLecture(lectureByDayList);
		dayByCourseId.setStatus(ApiConstants.SUCCESS);
		dayByCourseId.setMessage("course!");
		return dayByCourseId;
	}

	private DayByCourseId errorResponse(String message) {

		dayByCourseId.setMessage(message);
		dayByCourseId.setStatus(ApiConstants.FAILURE);

		return dayByCourseId;
	}

}
