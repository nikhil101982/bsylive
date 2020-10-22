package com.yoga.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.yoga.api.entity.CourseEntity;
import com.yoga.api.entity.LectureEntity;

@Component
@Repository
public interface LectureRepository extends JpaRepository<LectureEntity, Integer> {

	@Query("SELECT l FROM LectureEntity l WHERE l.lecId = ?1")
	List<LectureEntity> getLecEntityListByLecId(Integer dayId);
	
	@Query("SELECT l FROM LectureEntity l WHERE l.lecId = ?1")
	LectureEntity getLecEntityByLecId(Integer dayId);
	



}
