package com.yoga.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import com.yoga.api.constant.ApiConstants;
import com.yoga.api.entity.UserAccountEntity;
import com.yoga.api.model.CreateAccountReq;
import com.yoga.api.model.CreateAccountRequest;
import com.yoga.api.model.StatusMessageResponse;
import com.yoga.api.model.UserAccountId;
import com.yoga.api.model.UserAccountResponse;
import com.yoga.api.repository.CourseRepository;
import com.yoga.api.repository.UserAccountRepository;
import com.yoga.api.util.UtilMethods;

@Service
public class UserAccountService {

	@Autowired
	CourseRepository courseRepository;

	@Autowired
	UserAccountRepository userAccountRepository;

	StatusMessageResponse statusMessageResponse;

	UserAccountResponse userAccountResponse;

	UserAccountEntity userAccountEntity;

	String successResponseMessage = null;
	String failureResponseMessage = null;

	UtilMethods utilMethods = new UtilMethods();

	// Create User Account

	public StatusMessageResponse createUserAccount(CreateAccountReq createAccountRequest) {

		successResponseMessage = "account created successfully !";
		failureResponseMessage = "create account failed !";

		if (Objects.isNull(createAccountRequest)) {
			return utilMethods.errorResponse(failureResponseMessage);
		}

		statusMessageResponse = new StatusMessageResponse();

		try {

			userAccountEntity = userAccountRepository.getUserAccountEntityByEmail(createAccountRequest.getUserEmail());

		} catch (NullPointerException e) {
			return utilMethods.errorResponse(failureResponseMessage);

		}

		if (!Objects.isNull(userAccountEntity)) {
			return utilMethods.errorResponse(failureResponseMessage);
		}

		userAccountEntity = new UserAccountEntity();

		if (createAccountRequest.getUserName().equals("admin")) {
			userAccountEntity.setRole("admin");
		} else {
			userAccountEntity.setRole("student");
		}

		userAccountEntity.setEmailId(createAccountRequest.getUserEmail());
		userAccountEntity.setUserName(createAccountRequest.getUserName());
		userAccountEntity.setPassword(createAccountRequest.getPassword());

		try {
			userAccountRepository.save(userAccountEntity);
		} catch (Exception e) {
			return utilMethods.errorResponse(failureResponseMessage);
		}

		return utilMethods.successResponse(successResponseMessage);

	}

	// Get User Accounts

	public UserAccountResponse userAccounts() {

		successResponseMessage = "user accounts !";
		failureResponseMessage = "not able to get user accounts !";

		userAccountResponse = new UserAccountResponse();

		List<UserAccountEntity> userAccountEntityList = null;

		try {
			userAccountEntityList = userAccountRepository.getAllUserAccountEntity();
		} catch (NullPointerException e) {

			return errorResponse(failureResponseMessage);
		}

		if (Objects.isNull(userAccountEntityList)) {
			return errorResponse(failureResponseMessage);
		}

		CreateAccountRequest createAccRequest = null;

		List<CreateAccountRequest> createAccountRequestList = new ArrayList<CreateAccountRequest>();

		for (UserAccountEntity userAccEntity : userAccountEntityList) {

			createAccRequest = new CreateAccountRequest();
			createAccRequest.setPassword(userAccEntity.getPassword());
			createAccRequest.setUserEmail(userAccEntity.getEmailId());
			createAccRequest.setUserName(userAccEntity.getUserName());
			createAccRequest.setLogin(userAccEntity.isLogin());
			createAccRequest.setRole(userAccEntity.getRole());
			createAccountRequestList.add(createAccRequest);

		}
		
		if (Objects.isNull(createAccountRequestList)) {
			return errorResponse(failureResponseMessage);
		}

		return successResponse(createAccountRequestList ,successResponseMessage);

	}

	public UserAccountResponse errorResponse(String message) {

		userAccountResponse.setMessage(message);
		userAccountResponse.setStatus(ApiConstants.FAILURE);
		userAccountResponse.setUserAccounts(null);
		return userAccountResponse;
	}

	public UserAccountResponse successResponse(List<CreateAccountRequest> userAccounts, String message) {
		userAccountResponse.setMessage(message);
		userAccountResponse.setStatus(ApiConstants.FAILURE);
		userAccountResponse.setUserAccounts(userAccounts);

		return userAccountResponse;
	}
	
	

	public List<UserAccountId> getUserEmailId() {

		UserAccountId userAccountId;
		List<UserAccountId> userAccountIdList = new ArrayList<>();
		List<UserAccountEntity> userAccountEntityList = userAccountRepository.findAll();

		for (UserAccountEntity userAccountEntity : userAccountEntityList) {
			userAccountId = new UserAccountId();
			userAccountId.setUserEmail(userAccountEntity.getEmailId());
			userAccountIdList.add(userAccountId);
		}
		return userAccountIdList;

	}

	public StatusMessageResponse removeUser(String userEmail) {
		

		if (Objects.isNull(userEmail)) {
			return utilMethods.errorResponse("User id is empty! ");
		}

		statusMessageResponse = new StatusMessageResponse();

		try {

			userAccountEntity = userAccountRepository.getUserAccountEntityByEmail(userEmail);

		} catch (NullPointerException e) {
			return utilMethods.errorResponse(failureResponseMessage);

		}

		if (Objects.isNull(userAccountEntity)) {
			return utilMethods.errorResponse(failureResponseMessage);
		}
		
		
		try {
			userAccountRepository.delete(userAccountEntity);
		} catch (Exception e) {
			return utilMethods.errorResponse(failureResponseMessage);
		}

		return utilMethods.successResponse("Successfully removed user! ");



	}

}



