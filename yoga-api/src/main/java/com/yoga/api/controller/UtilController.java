package com.yoga.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yoga.api.model.DaysResp;
import com.yoga.api.service.GetCourseFindDaysService;
import com.yoga.api.service.SubscribeCourseByUser;

@RestController
@CrossOrigin
public class UtilController {

	@Autowired
	SubscribeCourseByUser courseService;
	
	@Autowired
	GetCourseFindDaysService getCourseFindDaysService;
	



	/*
	 * Delete all the data from User Account , Course , Lecture [All entity]
	 */
	@PostMapping("/delete")
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	public void addCourseByUsername() throws Exception {		
		 courseService.deleteData();
	}
	
	/*
	 * Get Days of Courses API
	 */
	
	@GetMapping("/getDays/{CourseId}")
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	public DaysResp getDays(@PathVariable("CourseId") Integer CourseId) throws Exception {
		return getCourseFindDaysService.findDays(CourseId);
		

	}

	


}
