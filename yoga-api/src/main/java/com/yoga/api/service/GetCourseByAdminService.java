package com.yoga.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

	Lecture lecture;

	LectureByDay lectureByDay;

	List<LectureByDay> lectureByDayList;

	List<DayEntity> dayEntityList;

	DayEntity dayEntity;

	public DayByCourseId getCourseByAdmin(Integer courseId, Integer dayId) {

		DayByCourseId dayByCourseId = new DayByCourseId();

		courseEntity = courseRepository.getCourseEntityByCourseId(courseId);

		dayEntityList = courseEntity.getDayEntity();

		for (DayEntity dayEntity : dayEntityList) {

			if (dayEntity.getDayId().equals(dayId)) {

				lectureByDayList = new ArrayList<>();

				if (dayEntity.getDayId().equals(dayId) && !Objects.isNull(courseEntity)) {

					for (LectureEntity lectureEntity : dayEntity.getLecEntity()) {

						lectureByDay = new LectureByDay();

						lectureByDay.setSNo(lectureEntity.getSNo());
						lectureByDay.setCurrentDate(lectureEntity.getCurrDate());
						lectureByDay.setDisableJoinBtn(false);
						lectureByDay.setEndTime(lectureEntity.getEndTime());
						lectureByDay.setIFrameDynamicLink(lectureEntity.getIframeDynamicLink());
						lectureByDay.setLectureName(lectureEntity.getLectureName());
						lectureByDay.setStartTime(lectureEntity.getStartTime());
						lectureByDayList.add(lectureByDay);
					}

					dayByCourseId.setDayName(dayEntity.getDayName());
					dayByCourseId.setDayId(dayEntity.getDayId());
					dayByCourseId.setLecture(lectureByDayList);

				} else {
					System.out.println(" Day id is not present in Course ");
				}

			}

		}

		return dayByCourseId;

	}

}
