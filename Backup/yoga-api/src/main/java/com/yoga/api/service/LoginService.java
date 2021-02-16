package com.yoga.api.service;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yoga.api.constant.ApiConstants;
import com.yoga.api.entity.UserAccountEntity;
import com.yoga.api.model.LoginRequest;
import com.yoga.api.model.LoginResponse;
import com.yoga.api.model.LogoutRequest;
import com.yoga.api.model.StatusMessageResponse;
import com.yoga.api.model.UserDetail;
import com.yoga.api.repository.UserAccountRepository;

@Service
public class LoginService {

	LoginResponse loginResponse;

	@Autowired
	UserAccountRepository userAccountRepository;

	UserAccountEntity userAccountEntity;

	UserDetail userDetail;

	StatusMessageResponse statusMessageResponse;

	/*
	 * Login API
	 */

	public LoginResponse validate(LoginRequest loginRequest) {

		if (Objects.isNull(loginRequest)) {
			return failureLoginResponse();

		}
		
		String userID_DB = null;
		boolean userIsLogin = false;
		boolean password_equal = false;
		String password_DB;
		String password_request;
		password_equal = false;


		userDetail = new UserDetail();
		loginResponse = new LoginResponse();

		String userEmail = loginRequest.getUserEmail();

		try {
			userAccountEntity = userAccountRepository.findByUserEmail(userEmail);
		} catch (Exception e) {
			return failureLoginResponse();
		}

		if (Objects.isNull(userAccountEntity)) {
			return failureLoginResponse();
		}
		
		password_DB = userAccountEntity.getPassword();
		password_request = loginRequest.getPassword();
		userIsLogin = userAccountEntity.getIsLogin() == ApiConstants.TRUE;
		password_equal = password_DB.equals(password_request);
		userID_DB = userAccountEntity.getUserEmail();
		
		if (userIsLogin || !password_equal || !userEmail.equals(userID_DB)) {
			return failureLoginResponse();
		}

		try {

			userAccountEntity.setIsLogin(ApiConstants.TRUE);

			userAccountRepository.save(userAccountEntity);
			userDetail.setUserEmail(loginRequest.getUserEmail());
			userDetail.setUserName(userAccountEntity.getUserName());
			userDetail.setUserRole(userAccountEntity.getRole());
			return successLoginResponse();
		} catch (Exception e) {
			return failureLoginResponse();
		}

	}

	private LoginResponse successLoginResponse() {
		loginResponse.setStatus(ApiConstants.SUCCESS);
		loginResponse.setUserDetails(userDetail);
		loginResponse.setMessage("Login successful");
		return loginResponse;
	}

	private LoginResponse failureLoginResponse() {
		loginResponse.setStatus(ApiConstants.FAILURE);
		loginResponse.setMessage("Login failed !");
		return loginResponse;
	}

	public StatusMessageResponse logout(LogoutRequest logoutRequest) {

		statusMessageResponse = new StatusMessageResponse();

		if (Objects.isNull(logoutRequest)) {
			return failureLogoutResponse();

		}

		try {
			userAccountEntity = userAccountRepository.findByUserEmail(logoutRequest.getUserEmail());
		} catch (Exception e) {
			return failureLogoutResponse();
		}

		if (Objects.isNull(userAccountEntity)) {
			return failureLogoutResponse();

		}
		System.out.println("user login status *********************  " + userAccountEntity.getIsLogin());

		if (userAccountEntity.getIsLogin().equals(ApiConstants.TRUE)) {			
			userAccountEntity.setIsLogin(ApiConstants.FALSE);
			try {
				userAccountRepository.save(userAccountEntity);
			} catch (Exception e) {
				return failureLogoutResponse();

			}

			return successlogoutResponse();
		}

		return failureLogoutResponse();

	}

	private StatusMessageResponse successlogoutResponse() {
		statusMessageResponse.setStatus(ApiConstants.SUCCESS);
		statusMessageResponse.setMessage("");
		return statusMessageResponse;
	}

	private StatusMessageResponse failureLogoutResponse() {
		statusMessageResponse.setStatus(ApiConstants.FAILURE);
		statusMessageResponse.setMessage("Logout failed");
		return statusMessageResponse;
	}

}
