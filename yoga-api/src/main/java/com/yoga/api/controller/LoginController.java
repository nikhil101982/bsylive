package com.yoga.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.yoga.api.model.ChangePasswordRequest;
import com.yoga.api.model.ChangePasswordResponse;
import com.yoga.api.model.ForgotPasswordRequest;
import com.yoga.api.model.LoginRequest;
import com.yoga.api.model.LoginResponse;
import com.yoga.api.model.LogoutRequest;
import com.yoga.api.model.StatusResponse;
import com.yoga.api.service.LoginService;
import com.yoga.api.service.PasswordService;

@RestController
@CrossOrigin(origins="*" , allowedHeaders="*")
public class LoginController {

	@Autowired
	LoginService loginService;
	
	@Autowired
	PasswordService passwordService;

	
	/*
	 * Login API
	 */
	
	@PostMapping("/login" )
	public LoginResponse login(final @RequestBody LoginRequest loginRequest) throws Exception {
		return loginService.validate(loginRequest);
	}
	
	
	/*
	 * Forgot Password API
	 */
	
	@PostMapping("/forgotPassword")
	public LoginResponse forgotPassword(final @RequestBody ForgotPasswordRequest forgotPasswordRequest)
			throws Exception {
		return passwordService.fotgotPassword(forgotPasswordRequest);
	}
	
	/*
	 * Logout change of status ( isLogin) API
	 */
	
	@PostMapping("/logout")
	public ChangePasswordResponse logout(final @RequestBody LogoutRequest logoutRequest)
			throws Exception {
		
		ChangePasswordResponse logoutResponse =  loginService.logout(logoutRequest);
		
		return logoutResponse;
	}
	
	
	/*
	 * Forgot Password API
	 */
	
	@PostMapping("/changePassword")
	public ChangePasswordResponse changePassword(final @RequestBody ChangePasswordRequest changePasswordRequest)
			throws Exception {
		return passwordService.changePassword(changePasswordRequest);
	}

}
