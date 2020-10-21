package com.yoga.api.service;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	
	StatusMessageResponse statusMessageResponse = new StatusMessageResponse();

	/*
	 * Login API
	 */

	/*
	 * Login API
	 */

	public LoginResponse validate(LoginRequest loginRequest) {

		userDetail = new UserDetail();
		loginResponse = new LoginResponse();

		String emailFromLoginRequest = loginRequest.getUserEmail();

		userAccountEntity = userAccountRepository.getUserAccountEntityByEmail(emailFromLoginRequest);

		if (!Objects.isNull(userAccountEntity) && !userAccountEntity.isLogin()) {

			String emailFromEntity = userAccountEntity.getEmailId(); // password
			String passwordToLoginFromUser = loginRequest.getPassword();
			String passwordFromEntity = userAccountEntity.getPassword();
			boolean userIsLogin = userAccountEntity.isLogin();

			if (emailFromLoginRequest.equals(emailFromEntity) && passwordToLoginFromUser.equals(passwordFromEntity)
					&& !userIsLogin) {

				userAccountEntity.setLogin(true);
				userAccountRepository.save(userAccountEntity);

				userDetail.setUserEmail(loginRequest.getUserEmail());
				userDetail.setUserName(userAccountEntity.getUserName());

				loginResponse.setStatus("success");
				loginResponse.setUserDetails(userDetail);
				loginResponse.setMessage("you have successfully logged in");

				return loginResponse;
			}else {
				
				loginResponse.setStatus("failure");
				//loginResponse.setUserDetails(userDetail);
				loginResponse.setMessage("wrong username or password or user is already logged in");
				return loginResponse;


			}

		} else {

			userDetail.setUserEmail(loginRequest.getUserEmail());
			userDetail.setUserName("");

			loginResponse.setStatus("failure");
			//loginResponse.setUserDetails(userDetail);
			loginResponse.setMessage("wrong username or password or user is already logged in");
		}
		return loginResponse;

	}

	public StatusMessageResponse logout(LogoutRequest logoutRequest) {

		userAccountEntity = userAccountRepository.getUserAccountEntityByEmail(logoutRequest.getUserEmail());

		if (userAccountEntity.isLogin()) {

			userAccountEntity.setLogin(false);
			userAccountRepository.save(userAccountEntity);
			statusMessageResponse.setStatus("success");
			statusMessageResponse.setMessage("You have successfully logged out");

			return statusMessageResponse;

		}

		statusMessageResponse.setStatus("fail");
		statusMessageResponse.setMessage("You have already logged out");
		return statusMessageResponse;

	}

}
