package com.yoga.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.yoga.api.entity.CourseEntity;

@Component
@Repository
public interface CourseRepository extends JpaRepository<CourseEntity, Integer> {

	@Query("SELECT c FROM CourseEntity c WHERE c.courseId = ?1")
	CourseEntity getCourseEntityByCourseId(Integer courseId);

	@Query("SELECT c FROM CourseEntity c WHERE c.courseName = ?1")
	CourseEntity getCourseEntityByCourseName(String courseName);

	@Query("SELECT c FROM CourseEntity c")
	List<CourseEntity> getAllCourses();

	@Query("SELECT c FROM CourseEntity c WHERE c.courseName = ?1 and c.startDate = ?2")
	CourseEntity getCourseByCourseNameAndStartDate(String courseName, String startDate);

	@Query("SELECT c FROM CourseEntity c WHERE c.courseName = ?1 and c.startDate = ?2  and c.couseDuration = ?3")
	CourseEntity getCourseByCourseNameAndStartDateAndCouseDuration(String courseName, String startDate,
			String couseDuration);

}
