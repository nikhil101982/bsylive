package com.yoga.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.yoga.api.entity.LectureEntity;
import com.yoga.api.entity.LectureTypeEntity;

@Component
@Repository
public interface LectureTypeRepository extends JpaRepository<LectureTypeEntity, Integer> {

}
