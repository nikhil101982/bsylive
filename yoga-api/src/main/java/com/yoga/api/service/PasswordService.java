package com.yoga.api.service;

import java.util.Objects;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.yoga.api.constant.ApiConstants;
import com.yoga.api.entity.UserAccountEntity;
import com.yoga.api.model.ChangePasswordRequest;
import com.yoga.api.model.ForgotPasswordRequest;
import com.yoga.api.model.LoginResponse;
import com.yoga.api.model.StatusMessageResponse;
import com.yoga.api.repository.UserAccountRepository;
import com.yoga.api.util.UtilMethods;

@Service
public class PasswordService {

	@Autowired
	public JavaMailSender javaMailSender;

	@Value("${forgot.password.email.send.to}")
	private String forgotPasswordSendEmailTo;

	@Value("${forgot.password.email.send.from}")
	private String forgotPasswordSendEmailFrom;

	LoginResponse loginResponse;

	@Autowired
	UserAccountRepository userAccountRepository;

	UserAccountEntity userAccountEntity;

	StatusMessageResponse statusMessageResponse = new StatusMessageResponse();

	String successResponseMessage = null;
	String failureResponseMessage = null;

	UtilMethods utilMethods = new UtilMethods();

	private StatusMessageResponse sendEmail(String userPassword, String emailId) throws MessagingException {

		SimpleMailMessage message = new SimpleMailMessage();
		try {
			message.setTo(emailId);
			message.setFrom(forgotPasswordSendEmailFrom);
			message.setText("password: " + userPassword);
			message.setSubject("Bihar yoga login password");
			javaMailSender.send(message);
			return utilMethods.successResponse(successResponseMessage);
		} catch (MailException e) {

			return utilMethods.errorResponse(failureResponseMessage);

		}
	}

	/*
	 * Forgot Password API
	 */

	public StatusMessageResponse fotgotPassword(ForgotPasswordRequest forgotPasswordRequest) {

		successResponseMessage = "We have sent password successfully via email, If you do not receive an email, check your spam folder or make sure this email address is registered !";
		failureResponseMessage = "unable to send password !";

		if (Objects.isNull(forgotPasswordRequest)) {
			return utilMethods.errorResponse(failureResponseMessage);
		}

		String emailId = forgotPasswordRequest.getUserEmail();

		try {
			userAccountEntity = userAccountRepository.getUserAccountEntityByEmail(emailId);
		} catch (Exception e) {
			return utilMethods.errorResponse(failureResponseMessage);
		}

		if (Objects.isNull(userAccountEntity)) {
			return utilMethods.errorResponse(failureResponseMessage);
		}

		try {

			return sendEmail(userAccountEntity.getPassword(), emailId);

		} catch (MessagingException e) {
			return utilMethods.errorResponse(failureResponseMessage);

		}

	}

	/*
	 * Change Password API
	 */

	public StatusMessageResponse changePassword(ChangePasswordRequest changePasswordRequest) {

		successResponseMessage = "changed password success !";

		failureResponseMessage = "changed password failed !";

		if (Objects.isNull(changePasswordRequest) || changePasswordRequest.getNewPassword().equals(changePasswordRequest.getPassword())) {
			return utilMethods.errorResponse(failureResponseMessage);
		}

		UserAccountEntity userAccEntity;

		try {
			userAccEntity = userAccountRepository.getUserAccountEntityByEmail(changePasswordRequest.getUserEmail());
		} catch (Exception e) {
			return utilMethods.errorResponse(failureResponseMessage);

		}

		if (Objects.isNull(userAccEntity)) {
			return utilMethods.errorResponse(failureResponseMessage);
		}

		boolean passwordCheck = false;

		passwordCheck = userAccEntity.getPassword().equals(changePasswordRequest.getPassword());
		
		if(!passwordCheck) {
			return utilMethods.errorResponse(failureResponseMessage);

		}

		try {
			if (passwordCheck && userAccEntity.isLogin()) {

				userAccEntity.setPassword(changePasswordRequest.getNewPassword());
				userAccEntity.setLogin(false);
				userAccountRepository.save(userAccEntity);

				return utilMethods.successResponse(successResponseMessage);

			}
		} catch (Exception e) {

			return utilMethods.errorResponse(failureResponseMessage);

		}
		return statusMessageResponse;

	}

}
