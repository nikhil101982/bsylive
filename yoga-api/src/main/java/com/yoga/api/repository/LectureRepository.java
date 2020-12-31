package com.yoga.api.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.yoga.api.entity.LectureEntity;

@Component
@Repository
public interface LectureRepository extends JpaRepository<LectureEntity, Integer> {

	@Query("SELECT l FROM LectureEntity l WHERE l.lectureId = ?1")
	List<LectureEntity> getLecEntityListByLecId(Integer dayId);

	@Query("SELECT l FROM LectureEntity l WHERE l.lectureId = ?1")
	LectureEntity getLecEntityByLecId(Integer dayId);

	@Query("SELECT c FROM LectureEntity c WHERE c.lectureName = ?1 and c.startTime = ?2  and c.endTime = ?3")
	LectureEntity getLectureByLectureNameAndStartTimeAndEndTime(String lectureName, String startTime, String endTime);

	@Query("SELECT c FROM LectureEntity c WHERE c.lectureName = ?1 and c.startTime = ?2  and c.endTime = ?3 and c.videoIframeDynamicLink = ?4  and c.liveIframeDynamicLink = ?5")
	LectureEntity getLectureByLectureNameAndStartTimeAndEndTimeAndVideoIframeDynamicLinkAndLiveIframeDynamicLink(
			String lectureName, String startTime, String endTime, String videoLink, String liveLink);
	
	//@Transactional
    @Modifying(clearAutomatically = true)
	@Query("UPDATE LectureEntity c set c.lectureName=:lectureName , c.startTime =:startTime  , c.endTime =:endTime , c.videoIframeDynamicLink = :videoIframeDynamicLink   , c.liveIframeDynamicLink = :liveIframeDynamicLink , c.currDate = :currDate  WHERE c.lectureId=:lectureId ")
	void updateLectureEntity(@Param("lectureName") String lectureName , @Param("startTime") String startTime , @Param("endTime") String endTime, @Param("videoIframeDynamicLink") String videoIframeDynamicLink, @Param("liveIframeDynamicLink") String liveIframeDynamicLink  , @Param("currDate") String currDate , @Param("lectureId") Integer lectureId);

    LectureEntity findByLectureNameAndStartTimeAndEndTime(String lectureName, String startTime, String endTime);

  
    
    

	
}