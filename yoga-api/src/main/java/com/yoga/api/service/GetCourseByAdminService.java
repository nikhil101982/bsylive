package com.yoga.api.service;

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

	public DayByCourseId getCourseByAdmin(Integer courseId, Integer dayId) {

		if (Objects.isNull(courseId) || Objects.isNull(dayId)) {
			return errorResponse();
		}

		try {
			courseEntity = courseRepository.getCourseEntityByCourseId(courseId);
		} catch (Exception e) {
			return errorResponse();
		}

		if (Objects.isNull(courseEntity)) {
			return errorResponse();
		}

		dayEntityList = courseEntity.getDayEntity();

		lectureByDayList = new ArrayList<>();

		// Day
		try {
			dayEntity = dayRepository.getDayEntityByDayId(dayId);
		} catch (Exception e) {
			return errorResponse();
		}

		if (Objects.isNull(dayEntity)) {
			return errorResponse();
		}

		lecEntityList = dayEntity.getLecEntity();

		int numberOfLecture = dayEntity.getLecEntity().size();

		int[] arrayOfLectureId = new int[numberOfLecture];

		int count = 0;

		for (LectureEntity lectureEntity : lecEntityList) {

			arrayOfLectureId[count] = lectureEntity.getLecId();
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

					lectureByDay.setDisableJoinBtn(true);
					lectureByDay.setVideoIframeDynamicLink("");
					lectureByDay.setLiveIframeDynamicLink("");

				} else {


					if (!Objects.isNull(lectureEntity2.getVideoIframeDynamicLink())) {
						lectureByDay.setVideoIframeDynamicLink(lectureEntity2.getVideoIframeDynamicLink());
						lectureByDay.setDisableJoinBtn(false);


					} else {
						lectureByDay.setLiveIframeDynamicLink(lectureEntity2.getLiveIframeDynamicLink());
						lectureByDay.setDisableJoinBtn(false);


					}


				}

				lectureByDay.setSNo(lectureEntity2.getSNo());
				lectureByDay.setCurrentDate(lectureEntity2.getCurrDate());
				lectureByDay.setEndTime(lectureEntity2.getEndTime());
				lectureByDay.setLectureName(lectureEntity2.getLectureName());
				lectureByDay.setStartTime(lectureEntity2.getStartTime());

				lectureByDayList.add(lectureByDay);
			} catch (Exception e) {
				return errorResponse();
			}

		}

		return successResponse();

	}

	private DayByCourseId successResponse() {

		dayByCourseId.setDayName(dayEntity.getDayName());
		dayByCourseId.setDayId(dayEntity.getDayId());
		dayByCourseId.setLecture(lectureByDayList);
		dayByCourseId.setStatus(ApiConstants.SUCCESS);
		dayByCourseId.setMessage("Course is not present !");
		return dayByCourseId;
	}

	private DayByCourseId errorResponse() {

		dayByCourseId.setMessage("Course is not present !");
		dayByCourseId.setStatus(ApiConstants.FAILURE);

		return dayByCourseId;
	}

}
