package com.yoga.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.yoga.api.entity.DayEntity;

@Component
@Repository
public interface DayRepository extends JpaRepository<DayEntity, Integer> {

	
	@Query("SELECT c FROM DayEntity c WHERE c.dayId = ?1")
	DayEntity getDayEntityByDayId(Integer dayId);

	
}
