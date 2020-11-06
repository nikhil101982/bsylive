package com.yoga.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.yoga.api.entity.CourseEntity;
import com.yoga.api.entity.DayEntity;
import com.yoga.api.model.AllCoursesResponse;
import com.yoga.api.model.AllUserCoursesResponse;
import com.yoga.api.model.DayByCourseId;
import com.yoga.api.repository.CourseRepository;
import com.yoga.api.repository.DayRepository;
import com.yoga.api.repository.UserAccountRepository;
import com.yoga.api.service.GetCourseBasedOnUserNameService;
import com.yoga.api.service.GetCourseByAdminService;
import com.yoga.api.service.GetCourseService;

@RestController
@CrossOrigin
public class GetCourseController {

	@Autowired
	GetCourseService getCourseService;
	
	@Autowired
	CourseRepository courseRepository;
	
	@Autowired
	GetCourseBasedOnUserNameService getCourseBasedOnUserNameService;
	
	@Autowired
	UserAccountRepository userAccountRepository;
	
	@Autowired
	DayRepository dayRepository;
	
	@Autowired
	GetCourseByAdminService getCourseByAdminService;
	
	DayEntity dayEntity;
	
	List<DayEntity> dayEntityList;

	
	/*
	 * Get Courses by courseId and dayId API
	 */
	
	@GetMapping("/getCourseByAdmin/{courseId}/{dayId}")
	@CrossOrigin(origins="*" , allowedHeaders="*")
	public DayByCourseId getCourseByAdmin(@PathVariable("courseId") Integer courseId , @PathVariable("dayId") Integer dayId) {
		
		
		return getCourseByAdminService.getCourseByAdmin(courseId , dayId );
				
	}
	
	/*
	 * Get all days API
	 */
	
	
	@GetMapping("/getAllDays")
	public List<DayEntity> geAllDay() {
		
		dayEntityList = dayRepository.findAll();
		
		return dayEntityList;
		
	}
	
	/*
	 * Get Courses Details for single courses API
	 */
	
	@GetMapping("/courseDetailsBasedOnCourseId/{courseId}")
	public CourseEntity courseDetails(@PathVariable("courseId") Integer courseId) throws Exception {		
		return getCourseService.coursesByCourseId(courseId);

	}
	
	
	/*
	 * Get All Courses by UserName without lecture details.
	 */
	@GetMapping("/coursesBasedOnUserName/{nameOfTheCours}")
	public AllUserCoursesResponse coursesByUserName(@PathVariable("userName") String userName) throws Exception {
		return getCourseBasedOnUserNameService.coursesByUserName(userName);

	}

	
	/*
	 * Get All Courses
	 */
	@GetMapping("/courses")
	public AllCoursesResponse courses() throws Exception {
		return getCourseService.courses();

	}
	
	
	
	
}
