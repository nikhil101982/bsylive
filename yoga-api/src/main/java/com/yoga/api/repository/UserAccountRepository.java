package com.yoga.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.yoga.api.entity.UserAccountEntity;

@Component
@Repository
public interface UserAccountRepository extends JpaRepository<UserAccountEntity, Integer> {

	
	@Query("SELECT u FROM UserAccountEntity u WHERE u.userName = ?1")
	UserAccountEntity getUserAccountEntityByUserName( Integer userName);
	
	@Query("SELECT u FROM UserAccountEntity u")
	List<UserAccountEntity> getAllUserAccountEntity();
	
	@Query("SELECT u FROM UserAccountEntity u WHERE u.emailId = ?1")
	UserAccountEntity getUserAccountEntityByEmail(String email);
	
	UserAccountEntity findByUserName(String userName);
	
	UserAccountEntity findByEmailId(String email);

	
	

}
