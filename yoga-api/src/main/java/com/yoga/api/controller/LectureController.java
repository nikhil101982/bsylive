package com.yoga.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yoga.api.entity.LecEntity;
import com.yoga.api.repository.LecRepository;

@RestController
@CrossOrigin
public class LectureController {

	@Autowired
	LecRepository lecRepository;


	/*
	 * Get Days of Courses API
	 */

	@GetMapping(value = "/getLectures")
	public List<LecEntity> getLectures() throws Exception {
		return lecRepository.findAll();
	}

	

}
