package com.yoga.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.yoga.api.entity.CourseEntity;
import com.yoga.api.entity.LecEntity;

@Component
@Repository
public interface LecRepository extends JpaRepository<LecEntity, Integer> {

	@Query("SELECT l FROM LecEntity l WHERE l.lecId = ?1")
	List<LecEntity> getLecEntityListByLecId(Integer dayId);
	
	@Query("SELECT l FROM LecEntity l WHERE l.lecId = ?1")
	LecEntity getLecEntityByLecId(Integer dayId);
	



}
