package com.yoga.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.yoga.api.model.AddCourseAdminRequest;
import com.yoga.api.model.AddCourseByDayId;
import com.yoga.api.model.AddListOfCourse;
import com.yoga.api.model.CoursesId;
import com.yoga.api.model.RegisterCourses;
import com.yoga.api.model.StatusMessageResponse;
import com.yoga.api.repository.CourseRepository;
import com.yoga.api.service.AddCourseAdminService;
import com.yoga.api.service.AddCourseFromAdminService;
import com.yoga.api.service.AddCourseService;
import com.yoga.api.service.SubscribeCourseByUser;
import com.yoga.api.service.UpdateCourService;
import com.yoga.api.service.UpdateCourTempService;
import com.yoga.api.service.UpdateCourseService;

@RestController
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
	AddCourseAdminService addCourseAdminService;

	@Autowired
	UpdateCourService updateCourService;

	@Autowired
	UpdateCourTempService updateCourTempService;

	@Autowired
	CourseRepository courseRepository;
	
	@Autowired
	SubscribeCourseByUser subscribeCourseByUser;


	// @Autowired
	// AddCourseWithLectureDetailsService addCourseWithLectureDetailsService;
	/*
	 * Add course by admin
	 */

	@PostMapping("/addFullCourse")
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	public StatusMessageResponse addCourseTemp(@RequestBody AddCourseByDayId addCourseByDayId) throws Exception {
		return addCourseFromAdminService.addCourse(addCourseByDayId);
	}

	@PostMapping("/addCourses")
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	public StatusMessageResponse addCourse(@RequestBody AddCourseAdminRequest addCourseAdminRequest) throws Exception {
		return addCourseAdminService.addCourse(addCourseAdminRequest);
	}

	/*
	 * Remove Courses by admin
	 */

	@PostMapping("/removeCourse")
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	public StatusMessageResponse removeCourse(@RequestBody List<CoursesId> coursesIdList) throws Exception {
		return addCourseService.removeCourse(coursesIdList);
	}
	

	/*
	 * Update Courses by admin in user profile
	 */

	@PutMapping("/updateUserCourses")
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	public StatusMessageResponse updateUserCourses(@RequestBody AddListOfCourse course) throws Exception {
		return updateCourseService.updateUserCourses(course);
	}

	
	/*
	 * Add course by admin
	 */

	@GetMapping("/updateCourse/{courseId}")
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	public AddCourseByDayId updateCourses(@PathVariable("courseId") Integer courseId) throws Exception {

		return updateCourTempService.updateCourse(courseId);

	}
	

	@PostMapping("/registerCourses/{userEmail}")
	public StatusMessageResponse registerCourses(@RequestBody RegisterCourses subscribeCourses,
			@PathVariable("userEmail") String userEmail) throws Exception {
		return subscribeCourseByUser.registerCourses(subscribeCourses, userEmail);
	}


}
