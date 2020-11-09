package com.yoga.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.yoga.api.model.AddCourseByDayId;
import com.yoga.api.model.AddCourseResponse;
import com.yoga.api.model.AddListOfCourse;
import com.yoga.api.model.CourseResources;
import com.yoga.api.model.RemoveCourseRequest;
import com.yoga.api.model.StatusMessageResponse;
import com.yoga.api.model.RegisterCourses;
import com.yoga.api.service.AddCourseFromAdminService;
import com.yoga.api.service.AddCourseService;
import com.yoga.api.service.SubscribeCourseByUser;

@RestController
@CrossOrigin
public class AddCourseController {

	@Autowired
	SubscribeCourseByUser subscribeCourseByUser;

	@Autowired
	AddCourseService addCourseService;
	
	
	@Autowired
	AddCourseFromAdminService addCourseFromAdminService;
	
	/*
	 * Add course by admin
	 */
	
	@PostMapping("/addCourseFromAdmin")
	public StatusMessageResponse getCourseByDayIDAndByCourseId(@RequestBody AddCourseByDayId addCourseByDayId) throws Exception {	
		return addCourseFromAdminService.addCourseFromAdmin(addCourseByDayId);
	}
	
	/*
	 * Register Courses by user in their account
	 */

	@PostMapping("/registerCourses/{userEmail}")
	public StatusMessageResponse registerCourses(@RequestBody RegisterCourses subscribeCourses,
			@PathVariable("userEmail") String userEmail) throws Exception {
		return subscribeCourseByUser.registerCourses(subscribeCourses, userEmail);
	}
	
	/*
	 * Remove Courses by admin
	 */
	
	@PostMapping("/removeCourse/{courseId}")
	public StatusMessageResponse removeCourse(@PathVariable("courseId") Integer courseId) throws Exception {	
		return addCourseService.removeCourse(courseId);
	}
	
	/*
	 * Update Courses by admin in user profile
	 */
	
	@PutMapping("/updateUserCourses")
	public StatusMessageResponse updateUserCourses(@RequestBody AddListOfCourse course) throws Exception {	
		return addCourseService.updateUserCourses(course);
	}

}
