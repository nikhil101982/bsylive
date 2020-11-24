package com.yoga.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.yoga.api.entity.LectureEntity;
import com.yoga.api.model.AddCourseByDayId;
import com.yoga.api.model.CreateLectureRequest;
import com.yoga.api.model.CreateLectureTempRequest;
import com.yoga.api.model.StatusMessageResponse;
import com.yoga.api.repository.LectureRepository;
import com.yoga.api.service.LectureAdminService;
import com.yoga.api.service.LectureAdminTempService;
import com.yoga.api.service.TempService;

@RestController
@CrossOrigin
public class LectureController {

	@Autowired
	LectureRepository lecRepository;
	
	@Autowired
	LectureAdminTempService lectureAdminService;
	
	@Autowired
	TempService tempService;

	
//	@Autowired
//	GetLectureDetailsService getLectureDetailsService;


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
	public StatusMessageResponse createLecture(@RequestBody CreateLectureTempRequest createLectureRequest) throws Exception {
		return tempService.createLecture(createLectureRequest);
	}
	
	
	/*
	 * Get Lecture Detail API
	 */

//	@GetMapping(value = "/getLecturesDetails/{lectureId}/{lectureName}")
//	public CourseLectureDetailResponse getLecturesDetails(@PathVariable("lectureId") Integer lectureId , @PathVariable("lectureName") String lectureName) throws Exception {
//		return getLectureDetailsService.getLecturesDetails(lectureId , lectureName );
//	}

	

}
