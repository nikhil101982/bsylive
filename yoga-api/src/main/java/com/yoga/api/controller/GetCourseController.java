package com.yoga.api.controller;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.yoga.api.entity.CourseEntity;
import com.yoga.api.entity.DayEntity;
import com.yoga.api.model.AllCoursesResponse;
import com.yoga.api.model.AllUserCoursesResponse;
import com.yoga.api.model.DayByCourseId;
import com.yoga.api.repository.CourseRepository;
import com.yoga.api.repository.DayRepository;
import com.yoga.api.repository.UserAccountRepository;
import com.yoga.api.service.GetCourseByAdminService;
import com.yoga.api.service.GetCourseByEmailService;
import com.yoga.api.service.GetCourseFromAdminService;
import com.yoga.api.service.GetCourseService;

@RestController
@CrossOrigin
public class GetCourseController {

	@Autowired
	GetCourseService getCourseService;

	@Autowired
	CourseRepository courseRepository;

	@Autowired
	GetCourseByEmailService getCourseBasedOnUserNameService;

	@Autowired
	UserAccountRepository userAccountRepository;

	@Autowired
	DayRepository dayRepository;

	@Autowired
	GetCourseByAdminService getCourseByAdminService;

	DayEntity dayEntity;

	List<DayEntity> dayEntityList;

	@Autowired
	GetCourseFromAdminService getCourseFromAdminService;


	/*
	 * 
	 * 
	 * /* Get Courses by courseId and dayId API
	 */

	@GetMapping("/getCourseByAdmin/{courseId}/{dayId}")
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	public DayByCourseId getCourseByAdmin(@PathVariable("courseId") Integer courseId,
			@PathVariable("dayId") Integer dayId) throws ParseException {

		return getCourseByAdminService.getCourseByAdmin(courseId, dayId);

	}

	/*
	 * Get all days API
	 */

	@GetMapping("/getAllDays")
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	public List<DayEntity> geAllDay() {

		dayEntityList = dayRepository.findAll();

		return dayEntityList;

	}

	/*
	 * Get Courses Details for single courses API
	 */

	@GetMapping("/courseDetailsBasedOnCourseId/{courseId}")
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	public CourseEntity courseDetails(@PathVariable("courseId") Integer courseId) throws Exception {
		return getCourseService.coursesByCourseId(courseId);

	}

	/*
	 * Get All Courses by userEmail and userRole without lecture details.
	 */
	@GetMapping("/courses/{userEmail}/{userRole}")
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	public AllUserCoursesResponse coursesByUserName(@PathVariable("userEmail") String userEmail,
			@PathVariable("userRole") String userRole) throws Exception {
		return getCourseBasedOnUserNameService.coursesByUserName(userEmail, userRole);

	}

	@GetMapping("/coursesForRegistration/{userEmail}/{userRole}")
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	public AllUserCoursesResponse coursesForRegistration(@PathVariable("userEmail") String userEmail,
			@PathVariable("userRole") String userRole) throws Exception {
		return getCourseBasedOnUserNameService.coursesByUserName(userEmail, userRole);

	}

	/*
	 * Get All Courses with course id and course name as a response
	 */
	@GetMapping("/coursesForAdmin")
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	public AllCoursesResponse courses() throws Exception {
		return getCourseService.courses();

	}

	/*
	 * Get All Courses with course id and course name as a response
	 */
	@GetMapping("/allCourses")
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	public List<CourseEntity> allCourses() throws Exception {
		return courseRepository.findAll();

	}

	/*
	 * Get All Courses with course id and course name as a response
	 */
	@GetMapping("/coursesToDelete")
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	public AllCoursesResponse coursesToDelete() throws Exception {
		return getCourseService.courses();

	}

	/*
	 * Get All Courses
	 */
	@GetMapping("/courses")
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	public AllCoursesResponse updateCoursesTest() throws Exception {
		return getCourseService.allCourses();
	}

}
