package com.yoga.api.service;

import java.util.Base64;
import java.util.Objects;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.yoga.api.entity.UserAccountEntity;
import com.yoga.api.model.ChangePasswordRequest;
import com.yoga.api.model.ForgotPasswordRequest;
import com.yoga.api.model.LoginResponse;
import com.yoga.api.model.StatusMessageResponse;
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
	
	StatusMessageResponse forgotPasswordResponse;
	
	StatusMessageResponse statusMessageResponse = new StatusMessageResponse();



	private void sendEmail(String userPassword, String emailId) throws MessagingException {

		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(emailId);
		message.setFrom(forgotPasswordSendEmailFrom);
		message.setText("password: " + userPassword);
		message.setSubject("Bihar yoga login password");

		javaMailSender.send(message);
	}

	/*
	 * Forgot Password API
	 */

	public StatusMessageResponse fotgotPassword(ForgotPasswordRequest forgotPasswordRequest) {

		try {
			
			String emailId = forgotPasswordRequest.getEmailid();

			try {
				userAccountEntity = userAccountRepository.getUserAccountEntityByEmail(emailId);
				userAccountEntity.getPassword();
			} catch (Exception e) {
				System.out.println("Entity is not unique");
				e.printStackTrace();
				forgotPasswordResponse.setStatus("fail");
				forgotPasswordResponse.setMessage("unable to send password");
				return forgotPasswordResponse;
			}

			try {
				sendEmail(userAccountEntity.getPassword(), emailId);
				statusMessageResponse.setStatus("success");
				statusMessageResponse.setMessage("send password securely via email");
				return statusMessageResponse;
			} catch (MessagingException e) {
				e.printStackTrace();
			}

			statusMessageResponse.setStatus("fail");
			statusMessageResponse.setMessage("unable to send password");

			return statusMessageResponse;

		} catch (NullPointerException e) {
			System.out.println("ForgotPasswordRequest don't have emailId " + forgotPasswordRequest.getEmailid()
					+ " or user account is not present in UserAccountEntity " + userAccountEntity);
		}

		return statusMessageResponse;

	}

	public StatusMessageResponse changePassword(ChangePasswordRequest changePasswordRequest) {

		userAccountEntity = userAccountRepository.getUserAccountEntityByEmail(changePasswordRequest.getUserEmail());
		
		byte[] result = Base64.getDecoder().decode(userAccountEntity.getPassword());
		
		String passwordFromDB = new String(result);


		boolean passwordCheck = passwordFromDB.equals(changePasswordRequest.getPassword());

		if (!Objects.isNull(userAccountEntity) && passwordCheck && userAccountEntity.isLogin() ) {

			userAccountEntity = new UserAccountEntity();
			userAccountEntity.setPassword(changePasswordRequest.getNewPassword());
			userAccountRepository.save(userAccountEntity);

			statusMessageResponse.setStatus("success");
			statusMessageResponse.setMessage("Your password has been changed successfully! ");

			return statusMessageResponse;

		} else {

			statusMessageResponse.setStatus("failure");
			statusMessageResponse.setMessage("Your password could not be changed! ");

			return statusMessageResponse;

		}

	}

}
