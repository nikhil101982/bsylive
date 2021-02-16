package com.yoga.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.yoga.api.entity.CourseEntity;
import com.yoga.api.entity.DayEntity;

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
			int couseDuration);

	@Query("SELECT c FROM CourseEntity c WHERE c.courseName = ?1 and c.startDate = ?2  and c.couseDuration = ?3")
	CourseEntity getCourse(String courseName, String startDate, int couseDuration);
	
	@Query("SELECT c FROM CourseEntity c WHERE c.courseName = ?1 and c.startDate = ?2  and c.couseDuration = ?3  and c.language = ?4")
	CourseEntity getCourseByCourseNameAndStartDateAndCouseDurationAndLanguage(String courseName, String startDate, int couseDuration, String language);

//	@Transactional
    @Modifying(clearAutomatically = true)
	@Query("UPDATE CourseEntity c set c.dayEntity= :dayEntity WHERE c.courseId=:courseId ")
	void updateCourseEntity(@Param("dayEntity") List<DayEntity> dayEntity, @Param("courseId") Integer courseId);
	
	
	
//	@Transactional
//    @Modifying(clearAutomatically = true)
//	@Query("UPDATE CourseEntity c set c.dayEntity= ?1 WHERE c.courseId=?2")
//	void updateECourseEntity(@Param("dayEntity") List<DayEntity> dayEntity, @Param("courseId") Integer courseId);

    

    
    


}
