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
@Table(name = "yoga_user_account")
public class UserAccountEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer userAccountId;

	String userName;
	String password;
	String emailId;
	boolean isLogin;
	String role;
	String loginStartDate;


	@Embedded
	@ElementCollection
	@OneToMany(targetEntity = CourseEntity.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "uc_fk", referencedColumnName = "userAccountId")
	private List<CourseEntity> courseEntity;;

}
