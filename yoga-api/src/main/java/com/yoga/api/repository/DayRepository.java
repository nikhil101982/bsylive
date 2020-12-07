package com.yoga.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.yoga.api.entity.DayEntity;
import com.yoga.api.entity.LectureEntity;

@Component
@Repository
public interface DayRepository extends JpaRepository<DayEntity, Integer> {

	
	@Query("SELECT c FROM DayEntity c WHERE c.dayId = ?1")
	DayEntity getDayEntityByDayId(Integer dayId);
	
	@Query("SELECT c FROM DayEntity c WHERE c.dayName = ?1")
	DayEntity getDayEntityByDayName(String dayName);
	
//	@Modifying
//	@Query("UPDATE DayEntity d set d.lecEntity= :lecEntity WHERE d.dayId=:dayId ")
//	int updateDayEntity(@Param("lecEntity") List<LectureEntity> lecEntity, @Param("dayId") Integer dayId);



	
}
