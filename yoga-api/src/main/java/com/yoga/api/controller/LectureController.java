package com.yoga.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yoga.api.entity.LectureEntity;
import com.yoga.api.repository.LectureRepository;

@RestController
@CrossOrigin
public class LectureController {

	@Autowired
	LectureRepository lecRepository;


	/*
	 * Get Days of Courses API
	 */

	@GetMapping(value = "/getLectures")
	public List<LectureEntity> getLectures() throws Exception {
		return lecRepository.findAll();
	}

	

}
