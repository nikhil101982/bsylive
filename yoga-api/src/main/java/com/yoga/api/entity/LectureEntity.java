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
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.yoga.api.model.DayId;

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
public class LectureEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Integer lectureId;

	public String lectureName;
	public String startTime;
	public String endTime;
	public String currDate;
	public String videoIframeDynamicLink;
	public String liveIframeDynamicLink;
	public String disableJoinBtn;
	public int sNo;
	public String lectureStatus;
	public String language;
	public Integer fromDay;
	public Integer toDay;
	//public List<String> listOfDays;
	
//	@Embedded
//	@ElementCollection
//	@OneToMany(targetEntity = DayEntity.class, cascade = CascadeType.ALL , fetch = FetchType.LAZY)
//	@JoinColumn(name = "dl_fk", referencedColumnName = "lectureId")
//	private List<DayEntity> dayEntity;








}
