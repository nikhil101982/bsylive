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

		userDetail = new UserDetail();
		loginResponse = new LoginResponse();

		String userId_request = loginRequest.getUserEmail();

		try {
			userAccountEntity = userAccountRepository.findByEmailId(userId_request);
		} catch (Exception e) {
			return failureLoginResponse();
		}

		String userID_DB = userAccountEntity.getEmailId();
		String password_DB = userAccountEntity.getPassword();

		String password_request = loginRequest.getPassword();
		boolean userIsLogin = userAccountEntity.isLogin();

		boolean password_equal = false;

		password_equal = password_DB.equals(password_request);

		if (Objects.isNull(userAccountEntity) || userIsLogin || !password_equal
				|| !userId_request.equals(userID_DB)) {
			return failureLoginResponse();
		}

		try {

			userAccountEntity.setLogin(true);
			userAccountRepository.save(userAccountEntity);
			userDetail.setUserEmail(loginRequest.getUserEmail());
			userDetail.setUserName(userAccountEntity.getUserName());
			return successLoginResponse();
		} catch (Exception e) {
			return failureLoginResponse();
		}

	}

	private LoginResponse successLoginResponse() {
		loginResponse.setStatus("success");
		loginResponse.setUserDetails(userDetail);
		loginResponse.setMessage("");
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

		String emailFromLoginRequest = logoutRequest.getUserEmail();

		try {
			userAccountEntity = userAccountRepository.getUserAccountEntityByEmail(emailFromLoginRequest);
		} catch (Exception e) {
			return failureLogoutResponse();
		}

		if (Objects.isNull(userAccountEntity)) {
			return failureLogoutResponse();

		}

		try {

			if (userAccountEntity.isLogin()) {

				userAccountEntity.setLogin(false);
				userAccountRepository.save(userAccountEntity);
				return successlogoutResponse();
			}

		} catch (Exception e) {
			return failureLogoutResponse();

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
