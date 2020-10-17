package com.yoga.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import com.yoga.api.model.AllUserCoursesResponse;
import com.yoga.api.model.Course;
import com.yoga.api.repository.CourseRepository;
import com.yoga.api.repository.UserAccountRepository;
import com.yoga.api.service.GetCourseService;

@RestController
@CrossOrigin
public class GetCourseController {

	@Autowired
	GetCourseService getCourseService;
	
	@Autowired
	CourseRepository courseRepository;
	
	
	@Autowired
	UserAccountRepository userAccountRepository;
	
	/*
	 * Get Courses Details for single courses API
	 */
	
//	@GetMapping("/courseDetailsBasedOnCourseId/{courseId}")
//	public Course courseDetails(@PathVariable("courseId") Integer courseId) throws Exception {		
//		return getCourseService.coursesByCourseId(courseId);
//
//	}
//	
	
	/*
	 * Get All Courses by UserName without lecture details.
	 */
	@GetMapping("/coursesBasedOnUserNameTemp/{nameOfTheCours}")
	public AllUserCoursesResponse coursesByUserName(@PathVariable("userName") String userName) throws Exception {
		return getCourseService.coursesByUserName(userName);

	}

	
	/*
	 * Get All Courses
	 */
	@GetMapping("/coursesBasedOnUserName/{userName}")
	public AllUserCoursesResponse courses(@PathVariable("userName") String userName ) throws Exception {
		return getCourseService.courses();

	}
	
	
}
