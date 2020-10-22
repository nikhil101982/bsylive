package com.yoga.api.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import com.yoga.api.entity.CourseEntity;
import com.yoga.api.entity.LectureEntity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserAccountCourseResponse {

	
	String userName;

    @Embedded
    @ElementCollection
    @OneToMany(targetEntity = LectureEntity.class,cascade = CascadeType.ALL)
    @JoinColumn(name ="uc_fk",referencedColumnName = "userAccountId")
    private List<CourseEntity> courseEntity;
	


}
