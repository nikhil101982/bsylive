package com.yoga.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.yoga.api.constant.ApiConstants;
import com.yoga.api.entity.UserAccountEntity;
import com.yoga.api.model.CreateAccountReq;
import com.yoga.api.model.CreateAccountRequest;
import com.yoga.api.model.StatusMessageResponse;
import com.yoga.api.model.UserAccountId;
import com.yoga.api.model.UserAccountResponse;
import com.yoga.api.model.UserEmail;
import com.yoga.api.repository.CourseRepository;
import com.yoga.api.repository.UserAccountRepository;
import com.yoga.api.util.SendEmailUtil;
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

	@Autowired
	SendEmailUtil sendEmailUtil;

	@Value("${forgot.password.email.send.from}")
	private String forgotPasswordSendEmailFrom;

	@Value("${admin.user.email}")
	private String adminUserEmail;

	UserAccountId userAccountId;

	List<UserEmail> userEmailList;

	UserEmail userEmail;

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

		System.out.println("email:  " + createAccountRequest.getUserEmail());

		if (createAccountRequest.getUserEmail().equals(adminUserEmail)) {
			userAccountEntity.setRole(ApiConstants.ADMIN_ROLE);
		} else {
			userAccountEntity.setRole(ApiConstants.STUDENT_ROLE);
		}

		userAccountEntity.setUserEmail(createAccountRequest.getUserEmail());
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
			createAccRequest.setUserEmail(userAccEntity.getUserEmail());
			createAccRequest.setUserName(userAccEntity.getUserName());
			createAccRequest.setIsLogin(userAccEntity.getIsLogin());
			createAccRequest.setRole(userAccEntity.getRole());
			
			createAccountRequestList.add(createAccRequest);

		}

		if (Objects.isNull(createAccountRequestList)) {
			return errorResponse(failureResponseMessage);
		}

		return successResponse(createAccountRequestList, successResponseMessage);

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

	public UserAccountId getUserEmailId() {

		List<UserAccountEntity> userAccountEntityList = null;

		userEmailList = new ArrayList<>();

		try {
			userAccountEntityList = userAccountRepository.findAll();
		} catch (Exception e) {
			userAccountEntityList = new ArrayList<>();
			return userAccountIdErrorResponse(userAccountEntityList);
		}

		if (Objects.isNull(userAccountEntityList)) {
			userAccountEntityList = new ArrayList<>();
			return userAccountIdErrorResponse(userAccountEntityList);
		}
		if (!Objects.isNull(userAccountEntityList)) {
			return userAccountIdSuccessResponse(userAccountEntityList);
		}
		return userAccountId;

	}

	private UserAccountId userAccountIdSuccessResponse(List<UserAccountEntity> userAccountEntityList) {

		userAccountId = new UserAccountId();

		userAccountId.setMessage("User emails !");
		userAccountId.setStatus(ApiConstants.SUCCESS);

		for (UserAccountEntity userAccEntity : userAccountEntityList) {
			
			userEmail = new UserEmail();
			userEmail.setUserEmail(userAccEntity.getUserEmail());
			
			userEmailList.add(userEmail);
		}
		userAccountId.setUserEmails(userEmailList);
		return userAccountId;
	}

	private UserAccountId userAccountIdErrorResponse(List<UserAccountEntity> userAccountEntityList) {

		userAccountId = new UserAccountId();
		userEmailList = new ArrayList<>();

		userAccountId.setMessage("User is not present! ");
		userAccountId.setStatus(ApiConstants.FAILURE);

		return userAccountId;
	}

	public StatusMessageResponse removeUser(String userEmail) throws MessagingException {

		successResponseMessage = "Successfully bihae yoga user account removed!";
		failureResponseMessage = "Bihae yoga user account not deleted";

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

		String emailId = userAccountEntity.getUserEmail();

		try {
			userAccountRepository.delete(userAccountEntity);
		} catch (Exception e) {
			return utilMethods.errorResponse(failureResponseMessage);
		}

		String text = "Your account has been deleted! ";

		return sendEmailUtil.sendEmail(emailId, forgotPasswordSendEmailFrom, "Bihar yoga account", text,
				successResponseMessage, failureResponseMessage);

	}

}
