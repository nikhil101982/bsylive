package com.yoga.api.service;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yoga.api.entity.UserAccountEntity;
import com.yoga.api.model.ChangePasswordResponse;
import com.yoga.api.model.LoginRequest;
import com.yoga.api.model.LoginResponse;
import com.yoga.api.model.LogoutRequest;
import com.yoga.api.repository.UserAccountRepository;

@Service
public class LoginService {

	LoginResponse loginResponse;

	@Autowired
	UserAccountRepository userAccountRepository;

	UserAccountEntity userAccountEntity;

	/*
	 * Login API
	 */

	/*
	 * Login API
	 */

	public LoginResponse validate(LoginRequest loginRequest) {

		String emailFromLoginRequest = loginRequest.getUserEmail();

		userAccountEntity = userAccountRepository.getUserAccountEntityByEmail(emailFromLoginRequest);

		if (!Objects.isNull(userAccountEntity) && !userAccountEntity.isLogin()) {

				String emailFromEntity = userAccountEntity.getEmailId();			// password
				String passwordToLoginFromUser = loginRequest.getPassword();
				String passwordFromEntity = userAccountEntity.getPassword();
				boolean userIsLogin = userAccountEntity.isLogin();
				
				if (emailFromLoginRequest.equals(emailFromEntity) && passwordToLoginFromUser.equals(passwordFromEntity)
						&& !userIsLogin) {

					userAccountEntity.setLogin(true);
					userAccountRepository.save(userAccountEntity);
					loginResponse = new LoginResponse();
					loginResponse.setStatus("success");
					loginResponse.setUserEmail(loginRequest.getUserEmail());
					loginResponse.setMessage("you have successfully logged in");
					return loginResponse;
				}

		} else {

			loginResponse.setStatus("failure");
			loginResponse.setUserEmail(loginRequest.getUserEmail());
			loginResponse.setMessage("Wrong username or password or user is already logged in");
		}
		return loginResponse;

	}

	public ChangePasswordResponse logout(LogoutRequest logoutRequest) {

		ChangePasswordResponse logoutResponse = new ChangePasswordResponse();

		if (logoutRequest.isLogoutStatus()) {

			userAccountEntity = new UserAccountEntity();
			userAccountEntity = userAccountRepository.getUserAccountEntityByEmail(logoutRequest.getUserEmail());
			userAccountEntity.setLogin(false);
			userAccountRepository.save(userAccountEntity);
			logoutResponse.setStatus("success");
			logoutResponse.setMessage("You have successfully logged out");
			logoutResponse.setUserEmail(logoutRequest.getUserEmail());

			return logoutResponse;

		}

		logoutResponse.setStatus("fail");
		logoutResponse.setMessage("You have already logged out");
		logoutResponse.setUserEmail(logoutRequest.getUserEmail());
		return logoutResponse;

	}

}
