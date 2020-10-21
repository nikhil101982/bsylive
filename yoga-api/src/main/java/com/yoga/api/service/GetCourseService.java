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
import com.yoga.api.model.Course;
import com.yoga.api.model.DayByCourseId;
import com.yoga.api.model.DaysResponse;
import com.yoga.api.model.Lecture;
import com.yoga.api.model.LectureByDay;
import com.yoga.api.repository.CourseRepository;
import com.yoga.api.repository.DayRepository;
import com.yoga.api.repository.LecRepository;
import com.yoga.api.repository.UserAccountRepository;

@Service
public class GetCourseService {

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

	List<LectureByDay> lectureByDayList = new ArrayList<>();

	UserAccountEntity userAccountEntity;

	List<String> courseNameInUserAccount;

	@Autowired
	DayRepository dayRepository;

	DayEntity dayEntity;

	// Get days api
	//
	// public List<DaysResponse> findDays(Integer courseId) {
	//
	// courseEntity = courseRepository.getCourseEntityByCourseId(courseId);
	//
	// List<DaysResponse> listOfDays = null;
	//
	// try {
	// } catch (NullPointerException e) {
	// System.out.println("Null pointer exception while get days ! CourseEntity " +
	// courseEntity + "Stacktrace ");
	// e.printStackTrace();
	// }
	//
	// if (!Objects.isNull(courseEntity)) {
	//
	// listOfDays = new ArrayList<DaysResponse>();
	//
	// int days = courseEntity.getCouseDuration();
	//
	// for (int i = 1; i <= days; i++) {
	//
	// DaysResponse daysResponse = new DaysResponse();
	// daysResponse.setDayName("Day" + i);
	// listOfDays.add(daysResponse);
	//
	// }
	//
	// return listOfDays;
	//
	// }
	//
	// return listOfDays;
	// }

	/*
	 * Get Courses API
	 */

	// public Course coursesByCourseId(Integer courseId) {
	//
	// List<Lecture> lectList = new ArrayList<>();
	//
	// Course courseDetail = new Course();
	//
	// courseEntity = courseRepository.getCourseEntityByCourseId(courseId);
	//
	// if (!Objects.isNull(courseEntity)) {
	//
	// courseDetail.setCourseName(courseEntity.getCourseName());
	// courseDetail.setCouseDuration(courseEntity.getCouseDuration());
	// courseDetail.setStartDate(courseEntity.getStartDate());
	// courseDetail.setCourseId(courseEntity.getCourseId());
	//
	// Lecture lec = new Lecture();
	//
	// for (LecEntity LecEnt : courseEntity.getLectureEntity()) {
	// lec.setCurrentDate(LecEnt.getCurrDate());
	// lec.setDisableJoinBtn(false);
	// lec.setEndTime(LecEnt.getEndTime());
	// lec.setLectureName(LecEnt.getLectureName());
	// lec.setStartTime(LecEnt.getStartTime());
	// lec.setLecId(LecEnt.getLecId());
	// lectList.add(lec);
	//
	// }
	//
	// courseDetail.setLecture(lectList);
	//
	// return courseDetail;
	//
	// }
	//
	// return courseDetail;
	//
	// }

	// Gell all courses based on user
	// public AllUserCoursesResponse coursesByUserName(String userName) {
	//
	// AllUserCourses allUserCourses;
	// AllUserCoursesResponse allUserCoursesResponse = new AllUserCoursesResponse();
	// List<AllUserCourses> allUserCoursesList = new ArrayList<>();
	//
	// userAccountEntity = userAccountRepository.findByUserName(userName);
	//
	// if (!Objects.isNull(userAccountEntity)) {
	//
	// for (Integer courseId : userAccountEntity.getCourseId()) {
	//
	// courseEntity = courseRepository.getCourseEntityByCourseId(courseId);
	//
	// if (!Objects.isNull(courseEntity)) {
	//
	// allUserCourses = new AllUserCourses();
	// allUserCourses.setCouseDurations(courseEntity.getCouseDuration());
	// allUserCourses.setCourseName(courseEntity.getCourseName());
	// allUserCourses.setStartDate(courseEntity.getStartDate());
	// allUserCourses.setCourseId(courseEntity.getCourseId());
	//
	// allUserCoursesList.add(allUserCourses);
	//
	// }
	//
	// }
	//
	// allUserCoursesResponse.setCourses(allUserCoursesList);
	//
	// return allUserCoursesResponse;
	//
	// } else {
	// return allUserCoursesResponse;
	//
	// }
	//
	// }

	// Get course
	public AllUserCoursesResponse courses() {

		AllUserCoursesResponse allUserCoursesResponse = new AllUserCoursesResponse();

		List<AllUserCourses> allUserCoursesList = new ArrayList<>();

		courseEntityList = courseRepository.getAllCourses();

		if (!Objects.isNull(courseEntityList)) {

			AllUserCourses allUserCourses;

			for (CourseEntity courseEntity : courseEntityList) {

				// courseEntity = new CourseEntity();

				allUserCourses = new AllUserCourses();
				allUserCourses.setCourseName(courseEntity.getCourseName());
				allUserCourses.setStartDate(courseEntity.getStartDate());
				allUserCourses.setCourseId(courseEntity.getCourseId());
				allUserCourses.setCouseDurations(courseEntity.getCouseDuration());

				allUserCoursesList.add(allUserCourses);

			}

			allUserCoursesResponse.setCourses(allUserCoursesList);

		}

		return allUserCoursesResponse;
	}

//	public DayByCourseId getCourseByAdmin(Integer courseId, Integer dayId) {
//		
//		DayByCourseId dayByCourseId = new DayByCourseId();
//
//		courseEntity = courseRepository.getCourseEntityByCourseId(courseId);
//
//		dayEntityList = courseEntity.getDayEntity();
//
//		//dayEntity = dayRepository.getDayEntityByDayId(dayId);
//
//		for (DayEntity dayEntity1 : dayEntityList) {
//
//			System.out.println("dayEntity1.getDayId()  " + dayEntity1.getDayId());
//			
//			if (dayEntity1.getDayId().equals(dayId)) {
//
//				for (LecEntity lectureEntity : dayEntity1.getLecEntity()) {
//
//					lectureByDay = new LectureByDay();
//
//					lectureByDay.setCurrentDate(lectureEntity.getCurrDate());
//					lectureByDay.setDisableJoinBtn(false);
//					lectureByDay.setEndTime(lectureEntity.getEndTime());
//					lectureByDay.setIFrameDynamicLink(lectureEntity.getIFrameDynamicLink());
//					lectureByDay.setLectureName(lectureEntity.getLectureName());
//					lectureByDay.setStartTime(lectureEntity.getStartTime());
//					lectureByDayList.add(lectureByDay);
//
//				}
//
//				dayByCourseId.setDayName(dayEntity1.getDayName());
//				dayByCourseId.setDayId(dayEntity1.getDayId());
//				dayByCourseId.setLecture(lectureByDayList);
//
//			}
//			
//
//		}
//
//		return dayByCourseId;
//
//	}

	// Get Days

	public List<DaysResponse> findDays(Integer courseId) {

		courseEntity = courseRepository.getCourseEntityByCourseId(courseId);
		//dayEntityList = courseEntity.getDayEntity();

		DaysResponse daysResponse = new DaysResponse();

		List<DaysResponse> daysResponseDayListD = new ArrayList<DaysResponse>();

		//List<DaysResponse> daysResponseDayList = new ArrayList<DaysResponse>();

		for (DayEntity dayEntityDays : courseEntity.getDayEntity()) {

			// dayEntityDays = new DayEntity();

			daysResponse = new DaysResponse();
			daysResponse.setDayId(dayEntityDays.getDayId());
			daysResponse.setDayName(dayEntityDays.getDayName().toUpperCase());
			daysResponseDayListD.add(daysResponse);

		}

		return daysResponseDayListD;
	}

	// Get course By course ID
	public CourseEntity coursesByCourseId(Integer courseId) {

		courseEntity = courseRepository.getCourseEntityByCourseId(courseId);

		
		return courseEntity;
	}

}
