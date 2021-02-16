package com.yoga.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.yoga.api.entity.LectureEntity;
import com.yoga.api.model.CreateLectureTempRequest;
import com.yoga.api.model.StatusMessageResponse;
import com.yoga.api.repository.LectureRepository;
import com.yoga.api.service.LectureService;

@RestController
@CrossOrigin
public class LectureController {

	@Autowired
	LectureRepository lecRepository;
	
	@Autowired
	LectureService createLectureService;

	/*
	 * Get all lecture
	 */

	@GetMapping(value = "/getLectures")
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	public List<LectureEntity> getLectures() throws Exception {
		return lecRepository.findAll();
	}

	@PutMapping("/createLecture")
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	public StatusMessageResponse createLecture(@RequestBody CreateLectureTempRequest createLectureRequest)
			throws Exception {
		return createLectureService.createLecture(createLectureRequest);
	}

	@PutMapping("/removeLecture/{lectureId}/{dayId}/{courseId}")
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	public StatusMessageResponse removeLecture(@PathVariable("lectureId") Integer lectureId , @PathVariable("dayId") Integer dayId , @PathVariable("courseId") Integer courseId)
			throws Exception {
		return createLectureService.removeLecture(lectureId,dayId,courseId);
	}
}
