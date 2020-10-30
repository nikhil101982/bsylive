package com.yoga.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yoga.api.constant.ApiConstants;
import com.yoga.api.entity.UserAccountEntity;
import com.yoga.api.model.CreateAccountReq;
import com.yoga.api.model.CreateAccountRequest;
import com.yoga.api.model.CreateAccountResponse;
import com.yoga.api.model.UserAccountResponse;
import com.yoga.api.repository.CourseRepository;
import com.yoga.api.repository.UserAccountRepository;

@Service
public class UserAccountService {

	@Autowired
	CourseRepository courseRepository;

	@Autowired
	UserAccountRepository userAccountRepository;

	CreateAccountResponse createAccountResponse;

	UserAccountEntity userAccountEntity;

	// Create User Account

	public CreateAccountResponse createUserAccount(CreateAccountReq createAccountRequest) {

		createAccountResponse = new CreateAccountResponse();

		try {

			userAccountEntity = userAccountRepository.getUserAccountEntityByEmail(createAccountRequest.getUserEmail());

		} catch (NullPointerException e) {
			System.out.println("Null pointer exception while creating account! UserAccountEntity " + userAccountEntity
					+ "Stacktrace ");
			e.printStackTrace();
		}
		if (Objects.isNull(userAccountEntity)) {

			userAccountEntity = new UserAccountEntity();

			if (createAccountRequest.getUserName().equals("admin")) {
				userAccountEntity.setRole("admin");
			} else {
				userAccountEntity.setRole("student");
			}

			userAccountEntity.setEmailId(createAccountRequest.getUserEmail());
			userAccountEntity.setUserName(createAccountRequest.getUserName());
			userAccountEntity.setPassword(createAccountRequest.getPassword());

			userAccountRepository.save(userAccountEntity);

			createAccountResponse.setStatus(ApiConstants.SUCCESS);
			createAccountResponse.setMessage("Account created successfully");

			return createAccountResponse;

		} else {
			createAccountResponse.setStatus(ApiConstants.FAILURE);
			createAccountResponse.setMessage("Not able to create account");

			return createAccountResponse;

		}

	}

	// Get User Accounts

	public UserAccountResponse userAccounts() {

		List<UserAccountEntity> userAccountEntityList = null;

		try {
			userAccountEntityList = userAccountRepository.getAllUserAccountEntity();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}

		UserAccountResponse userAccountResponse = new UserAccountResponse();

		if (!Objects.isNull(userAccountEntityList)) {

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

			userAccountResponse.setUserAccounts(createAccountRequestList);

			return userAccountResponse;

		}

		return userAccountResponse;
	}

}
