package com.yoga.api.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "yoga_lecture_details_reference")
public class LectureDetailReferenceEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Integer lectureDetailReferenceId;
	public String reference;

}
