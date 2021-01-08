package com.yoga.api.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
@Table(name = "yoga_course")
public class CourseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Integer courseId;
	public String courseName;
	public int couseDuration;
	public String startDate;
	public String language;
	public String endDate;
	public String prerequisite;
	
	
	@Embedded
	@ElementCollection
	@OneToMany(targetEntity = RegistrationFormEntity.class, cascade = CascadeType.ALL , fetch = FetchType.LAZY)
	@JoinColumn(name = "cd_fk", referencedColumnName = "courseId")
	private List<RegistrationFormEntity> registrationFormEntity;




	@Embedded
	@ElementCollection
	@OneToMany(targetEntity = DayEntity.class, cascade = CascadeType.ALL , fetch = FetchType.LAZY)
	@JoinColumn(name = "cd_fk", referencedColumnName = "courseId")
	private List<DayEntity> dayEntity;
	
	
	
	

}
