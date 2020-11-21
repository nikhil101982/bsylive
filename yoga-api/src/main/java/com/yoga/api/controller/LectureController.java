package com.yoga.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.yoga.api.entity.LectureEntity;
import com.yoga.api.model.AddCourseByDayId;
import com.yoga.api.model.CreateLectureRequest;
import com.yoga.api.model.StatusMessageResponse;
import com.yoga.api.repository.LectureRepository;
import com.yoga.api.service.LectureAdminService;

@RestController
@CrossOrigin
public class LectureController {

	@Autowired
	LectureRepository lecRepository;
	
	@Autowired
	LectureAdminService lectureAdminService;
	
//	@Autowired
//	GetLectureDetailsService getLectureDetailsService;


	/*
	 * Get all lecture
	 */

	@GetMapping(value = "/getLectures")
	public List<LectureEntity> getLectures() throws Exception {
		return lecRepository.findAll();
	}
	
	@PostMapping("/createLecture")
	public StatusMessageResponse createLecture(@RequestBody CreateLectureRequest createLectureRequest) throws Exception {
		return lectureAdminService.createLecture(createLectureRequest);
	}
	
	
	/*
	 * Get Lecture Detail API
	 */

//	@GetMapping(value = "/getLecturesDetails/{lectureId}/{lectureName}")
//	public CourseLectureDetailResponse getLecturesDetails(@PathVariable("lectureId") Integer lectureId , @PathVariable("lectureName") String lectureName) throws Exception {
//		return getLectureDetailsService.getLecturesDetails(lectureId , lectureName );
//	}

	

}
