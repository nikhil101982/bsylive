package com.yoga.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.yoga.api.model.AddCourseByDayId;
import com.yoga.api.model.AddListOfCourse;
import com.yoga.api.model.RegisterCourses;
import com.yoga.api.model.StatusMessageResponse;
import com.yoga.api.service.AddCourseFromAdminService;
import com.yoga.api.service.AddCourseService;
import com.yoga.api.service.AddCourseWithLectureDetailsService;
import com.yoga.api.service.SubscribeCourseByUser;
import com.yoga.api.service.UpdateCourseService;

@RestController
@CrossOrigin
public class AddCourseController {

	@Autowired
	SubscribeCourseByUser registerCourses;

	@Autowired
	AddCourseService addCourseService;
	
	
	@Autowired
	AddCourseFromAdminService addCourseFromAdminService;
	
	@Autowired
	UpdateCourseService updateCourseService;
	
	@Autowired
	AddCourseWithLectureDetailsService addCourseWithLectureDetailsService;
	/*
	 * Add course by admin
	 */
	
	@PostMapping("/addCourse")
	public StatusMessageResponse addCourse(@RequestBody AddCourseByDayId addCourseByDayId) throws Exception {	
		return addCourseFromAdminService.addCourse(addCourseByDayId);
	}
	
	/*
	 * Courses registration by user in their account
	 */

	@PostMapping("/registerCourses/{userEmail}")
	public StatusMessageResponse registerCourses(@RequestBody RegisterCourses subscribeCourses,
			@PathVariable("userEmail") String userEmail) throws Exception {
		return registerCourses.registerCourses(subscribeCourses, userEmail);
	}
	
	/*
	 * Remove Courses by admin
	 */
	
	@PostMapping("/removeCourse/{courseId}")
	@CrossOrigin(origins="*" , allowedHeaders="*")
	public StatusMessageResponse removeCourse(@PathVariable("courseId") Integer courseId) throws Exception {	
		return addCourseService.removeCourse(courseId);
	}
	
	/*
	 * Update Courses by admin in user profile
	 */
	
	@PutMapping("/updateUserCourses")
	@CrossOrigin(origins="*" , allowedHeaders="*")
	public StatusMessageResponse updateUserCourses(@RequestBody AddListOfCourse course) throws Exception {	
		return updateCourseService.updateUserCourses(course);
	}
	
	/*
	 * Add course by admin
	 */
	
	@PostMapping("/addCourseWithLectureDetails")
	public StatusMessageResponse addCourseWithLectureDetails(@RequestBody AddCourseByDayId addCourseByDayId) throws Exception {	
		return addCourseWithLectureDetailsService.addCourseWithLectureDetails(addCourseByDayId);
	}

}
