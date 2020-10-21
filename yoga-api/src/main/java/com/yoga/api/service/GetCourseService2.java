package com.yoga.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yoga.api.entity.CourseEntity;
import com.yoga.api.entity.DayEntity;
import com.yoga.api.entity.LecEntity;
import com.yoga.api.entity.UserAccountEntity;
import com.yoga.api.model.AllUserCourses;
import com.yoga.api.model.AllUserCoursesResponse;
import com.yoga.api.model.DayByCourseId;
import com.yoga.api.model.Lecture;
import com.yoga.api.model.LectureByDay;
import com.yoga.api.repository.CourseRepository;
import com.yoga.api.repository.DayRepository;
import com.yoga.api.repository.LecRepository;
import com.yoga.api.repository.UserAccountRepository;

@Service
public class GetCourseService2 {

	@Autowired
	CourseRepository courseRepository;

	@Autowired
	UserAccountRepository userAccountRepository;

	@Autowired
	LecRepository lecRepository;

	List<CourseEntity> courseEntityList;

	List<LecEntity> lecEntityList;

	List<DayEntity> dayEntityList;

	LecEntity lecEntity;

	CourseEntity courseEntity;

	Lecture lecture;

	LectureByDay lectureByDay;

	List<LectureByDay> lectureByDayList;

	UserAccountEntity userAccountEntity;

	List<String> courseNameInUserAccount;

	@Autowired
	DayRepository dayRepository;

	DayEntity dayEntity;

	public DayByCourseId getCourseByAdmin(Integer courseId, Integer dayId) {

		DayByCourseId dayByCourseId = new DayByCourseId();

		courseEntity = courseRepository.getCourseEntityByCourseId(courseId);

		dayEntityList = courseEntity.getDayEntity();

		// dayEntity = dayRepository.getDayEntityByDayId(dayId);

		for (DayEntity dayEntity : dayEntityList) {

			if (dayEntity.getDayId().equals(dayId)) {

				lectureByDayList = new ArrayList<>();

				if (dayEntity.getDayId().equals(dayId) && !Objects.isNull(courseEntity)) {

					for (LecEntity lectureEntity : dayEntity.getLecEntity()) {

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
