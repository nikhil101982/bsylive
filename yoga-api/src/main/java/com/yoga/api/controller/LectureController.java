package com.yoga.api.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
<<<<<<< HEAD
=======
import org.springframework.web.bind.annotation.PostMapping;
>>>>>>> 64d8723fc67e84e6b35f2a44fbfcf37bd22d5a48
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.yoga.api.entity.LectureEntity;
import com.yoga.api.model.CreateLectureTempRequest;
import com.yoga.api.model.DaysInLectures;
import com.yoga.api.model.LectureListInCourse;
import com.yoga.api.model.StatusMessageResponse;
import com.yoga.api.repository.LectureRepository;
import com.yoga.api.service.LectureService;

@RestController
@CrossOrigin
public class LectureController {

	@Autowired
	LectureRepository lecRepository;
	
	@Autowired
	LectureService lectureService;

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
		return lectureService.createLecture(createLectureRequest);
	}

<<<<<<< HEAD
	@PutMapping("/removeLecture/{lectureId}/{dayId}/{courseId}")
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	public StatusMessageResponse removeLecture(@PathVariable("lectureId") Integer lectureId , @PathVariable("dayId") Integer dayId , @PathVariable("courseId") Integer courseId)
			throws Exception {
		return createLectureService.removeLecture(lectureId,dayId,courseId);
=======
	@PostMapping("/removeLecture/{lectureId}")
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	public StatusMessageResponse removeLecture(@PathVariable("lectureId") Integer lectureId )
			throws Exception {
		return lectureService.removeLecture(lectureId);
	}
	
	@GetMapping(value = "/listOfLecture/{courseId}")
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	public LectureListInCourse listOfLecture(@PathVariable("courseId") Integer courseId) throws Exception {
		return lectureService.listOfLectures(courseId);
	}
	
	@GetMapping(value = "/selectedDaysId/{lectureId}/{courseId}")
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	public DaysInLectures selectedDaysId(@PathVariable("lectureId") Integer lectureId , @PathVariable("courseId") Integer courseId) throws Exception {
		return lectureService.getSelectedDaysInLecture(lectureId , courseId);
>>>>>>> 64d8723fc67e84e6b35f2a44fbfcf37bd22d5a48
	}
}
