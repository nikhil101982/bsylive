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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "yoga_day")
@Setter
@Getter
public class DayEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Integer dayId;

	public String dayName;
	

	@Embedded
	@ElementCollection
	@OneToMany(targetEntity = LectureEntity.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "dl_fk", referencedColumnName = "dayId")
	private List<LectureEntity> lecEntity;

}
