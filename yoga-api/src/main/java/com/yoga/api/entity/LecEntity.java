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
import javax.persistence.ManyToOne;
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
@Table(name = "t_yoga_lecture")
public class LecEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Integer lecId;
	
	public String lectureName;
	public String startTime;
	public String endTime;
	public String currDate;
	boolean disableJoinBtn;
	
	
	@Embedded
	@ElementCollection
	@ManyToOne(targetEntity = CourseEntity.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "ce_fk", referencedColumnName = "courseId")
	private List<Integer> courseId;

	

}
