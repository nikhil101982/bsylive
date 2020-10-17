package com.yoga.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yoga.api.service.AddCourseByUserNameService;

@RestController
@CrossOrigin
public class UtilController {

	@Autowired
	AddCourseByUserNameService courseService;
	
//	@Autowired
//	GetCourseService getCourseService;
	



	/*
	 * Delete all the data from User Account , Course , Lecture [All entity]
	 */
	@PostMapping("/delete")
	public void addCourseByUsername() throws Exception {		
		 courseService.deleteData();
	}
	
//	/*
//	 * Get Days of Courses API
//	 */
//	@GetMapping("/getDays/{nameOfTheCours}")
//	public List<DaysResponse> getDays(@PathVariable("nameOfTheCours") Integer nameOfTheCours) throws Exception {
//		System.out.println(nameOfTheCours);
//		return getCourseService.findDays(nameOfTheCours);
//
//	}

	


}
