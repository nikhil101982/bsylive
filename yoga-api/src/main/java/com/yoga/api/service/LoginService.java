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

		String emailFromLoginRequest = loginRequest.getUserEmail();

		// userAccountEntity =
		// userAccountRepository.getUserAccountEntityByEmail(emailFromLoginRequest);

		try {
			userAccountEntity = userAccountRepository.findByEmailId(emailFromLoginRequest);
		} catch (Exception e) {
			return failureLoginResponse();
		}

		if (Objects.isNull(userAccountEntity) || userAccountEntity.isLogin()) {
			return failureLoginResponse();
		}

		if (!Objects.isNull(userAccountEntity) && !userAccountEntity.isLogin()) {

			String emailFromEntity = userAccountEntity.getEmailId(); // password
			String passwordToLoginFromUser = loginRequest.getPassword();
			String passwordFromEntity = userAccountEntity.getPassword();
			boolean userIsLogin = userAccountEntity.isLogin();

			try {
				if (emailFromLoginRequest.equals(emailFromEntity) && passwordToLoginFromUser.equals(passwordFromEntity)
						&& !userIsLogin) {

					userAccountEntity.setLogin(true);
					try {
						userAccountRepository.save(userAccountEntity);
					} catch (Exception e) {
						return failureLoginResponse();
					}

					userDetail.setUserEmail(loginRequest.getUserEmail());
					userDetail.setUserName(userAccountEntity.getUserName());

					return successLoginResponse();
				}

			} catch (Exception e) {

				return failureLoginResponse();

			}

		}
		return loginResponse;

	}

	private LoginResponse successLoginResponse() {
		loginResponse.setStatus("success");
		loginResponse.setUserDetails(userDetail);
		loginResponse.setMessage("you have successfully logged in");
		return loginResponse;
	}

	private LoginResponse failureLoginResponse() {
		loginResponse.setStatus(ApiConstants.FAILURE);
		loginResponse.setMessage("Login failed");
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

		// userAccountEntity =
		// userAccountRepository.findByEmailId(emailFromLoginRequest);

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
		statusMessageResponse.setMessage("you have successfully logout");
		return statusMessageResponse;
	}

	private StatusMessageResponse failureLogoutResponse() {
		statusMessageResponse.setStatus(ApiConstants.FAILURE);
		statusMessageResponse.setMessage("Logout failed");
		return statusMessageResponse;
	}

}
