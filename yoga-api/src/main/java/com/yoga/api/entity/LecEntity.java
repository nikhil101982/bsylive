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
@Table(name = "yoga_lecture")
public class LecEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Integer lecId;
	
	public String lectureName;
	public String startTime;
	public String endTime;
	public String currDate;
	boolean disableJoinBtn;
	String iframeDynamicLink;
	int sNo;

	
	
}
