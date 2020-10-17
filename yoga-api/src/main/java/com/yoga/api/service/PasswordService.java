package com.yoga.api.service;

import java.util.Objects;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.yoga.api.entity.UserAccountEntity;
import com.yoga.api.model.ChangePasswordRequest;
import com.yoga.api.model.ChangePasswordResponse;
import com.yoga.api.model.ForgotPasswordRequest;
import com.yoga.api.model.LoginRequest;
import com.yoga.api.model.StatusResponse;
import com.yoga.api.model.LoginResponse;
import com.yoga.api.repository.UserAccountRepository;

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

	private void sendEmail(String userPassword, String emailId) throws MessagingException {

		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(emailId);
		message.setFrom(forgotPasswordSendEmailFrom);
		message.setText("Bihar yoga login password: " + userPassword);
		message.setSubject("Bihar yoga login password");

		javaMailSender.send(message);
	}

	/*
	 * Forgot Password API
	 */

	public LoginResponse fotgotPassword(ForgotPasswordRequest forgotPasswordRequest) {

		try {

			loginResponse = new LoginResponse();
			String emailId = forgotPasswordRequest.getEmailId();

			try {
				userAccountEntity = userAccountRepository.getUserAccountEntityByEmail(emailId);
				userAccountEntity.getPassword();
			} catch (Exception e) {
				System.out.println("Entity is not unique");
				e.printStackTrace();
				loginResponse.setStatus("fail");
				loginResponse.setMessage("unable to send password");
				return loginResponse;
			}

			try {
				sendEmail(userAccountEntity.getPassword(), emailId);
				loginResponse.setStatus("success");
				loginResponse.setMessage("send password securely via email");
				return loginResponse;
			} catch (MessagingException e) {

				e.printStackTrace();
			}

			loginResponse.setStatus("fail");
			loginResponse.setMessage("unable to send password");

			return loginResponse;

		} catch (NullPointerException e) {
			System.out.println("ForgotPasswordRequest don't have emailId " + forgotPasswordRequest.getEmailId()
					+ " or user account is not present in UserAccountEntity " + userAccountEntity);
		}

		return null;

	}

	public ChangePasswordResponse changePassword(ChangePasswordRequest changePasswordRequest) {

		userAccountEntity = userAccountRepository.getUserAccountEntityByEmail(changePasswordRequest.getUserEmail());
		ChangePasswordResponse changePasswordResponse = new ChangePasswordResponse();

		boolean passwordCheck = userAccountEntity.getPassword().equals(changePasswordRequest.getPassword());

		if (!Objects.isNull(userAccountEntity) && passwordCheck) {

			userAccountEntity = new UserAccountEntity();
			userAccountEntity.setPassword(changePasswordRequest.getNewPassword());
			userAccountRepository.save(userAccountEntity);

			changePasswordResponse.setStatus("success");
			changePasswordResponse.setUserEmail(changePasswordRequest.getUserEmail());
			changePasswordResponse.setMessage("Your password has been changed successfully! ");

			return changePasswordResponse;

		} else {

			changePasswordResponse.setStatus("failure");
			changePasswordResponse.setUserEmail(changePasswordRequest.getUserEmail());
			changePasswordResponse.setMessage("Your password could not be changed! ");

			return changePasswordResponse;

		}

	}

}
