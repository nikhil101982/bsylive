package com.yoga.api.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "yoga_lecture_type")
public class LectureTypeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Integer lectureTypeId;

	public String lectureTypeHeaderName;
	
	@Embedded
	@ElementCollection
	@OneToMany(targetEntity = LectureSessionEntity.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "uc_fk", referencedColumnName = "lectureTypeId")
	private List<LectureSessionEntity> lectureSessionEntity;



}
