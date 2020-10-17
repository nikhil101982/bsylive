package com.yoga.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.yoga.api.model.AddCourseByDayId;
import com.yoga.api.model.AddCourseResponse;
import com.yoga.api.model.CourseResources;
import com.yoga.api.model.StatusMessageResponse;
import com.yoga.api.model.StatusResponse;
import com.yoga.api.model.SubscribeCourses;
import com.yoga.api.service.AddCourseByUserNameService;
import com.yoga.api.service.AddCourseService;
import com.yoga.api.service.CourseService;

@RestController
@CrossOrigin
public class AddCourseController {

	@Autowired
	AddCourseByUserNameService addCourseByUserNameService;

	@Autowired
	AddCourseService addCourseService;
	
	
	@Autowired
	CourseService qeryService;
	
	
	@PostMapping("/addCourseFromAdmin")
	public StatusMessageResponse getCourseByDayIDAndByCourseId(@RequestBody AddCourseByDayId addCourseByDayId) throws Exception {	
		return qeryService.addCourse(addCourseByDayId);
	}
	
	/*
	 * Add Courses by user in their account
	 */

	@PostMapping("/subscribeTheCourse/{userEmail}")
	public StatusResponse subscribeTheCourse(@RequestBody SubscribeCourses subscribeCourses,
			@PathVariable("userEmail") String userEmail) throws Exception {
		return addCourseByUserNameService.subscribeTheCourse(subscribeCourses, userEmail);
	}
	
	/*
	 * Admin Add Courses API: Admin will use to add course in database (Register the
	 * course)
	 */
	

	@PostMapping("/addCourse")
	public AddCourseResponse addCourseNew(@RequestBody CourseResources course) throws Exception {	
		return addCourseService.addCourse(course);
	}

}
