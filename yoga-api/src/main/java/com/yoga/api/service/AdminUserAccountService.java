package com.yoga.api.service;

import java.util.Objects;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.yoga.api.entity.UserAccountEntity;
import com.yoga.api.model.CreateAccountReq;
import com.yoga.api.model.StatusMessageResponse;
import com.yoga.api.model.UserAccountResponse;
import com.yoga.api.repository.CourseRepository;
import com.yoga.api.repository.UserAccountRepository;
import com.yoga.api.util.SendEmailUtil;
import com.yoga.api.util.UtilMethods;

@Service
public class AdminUserAccountService {

	@Autowired
	CourseRepository courseRepository;

	@Autowired
	SendEmailUtil sendEmailUtil;

	@Value("${forgot.password.email.send.from}")
	private String forgotPasswordSendEmailFrom;

	@Autowired
	UserAccountRepository userAccountRepository;

	StatusMessageResponse statusMessageResponse;

	UserAccountResponse userAccountResponse;

	UserAccountEntity userAccountEntity;

	@Value("${admin.create.account.email.send.from}")
	private String adminCreateAccountSendEmailFrom;

	String successResponseMessage = null;
	String failureResponseMessage = null;

	UtilMethods utilMethods = new UtilMethods();

	@Autowired
	public JavaMailSender javaMailSender;

	// Create User Account by admin

	public StatusMessageResponse createAccountByAdmin(CreateAccountReq createAccountRequest) {

		successResponseMessage = "Account created successfully by admin !";
		failureResponseMessage = "Create account failed !";

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

		if (createAccountRequest.getUserName().equals("admin")) {
			userAccountEntity.setRole("admin");
		} else {
			userAccountEntity.setRole("student");
		}

		userAccountEntity.setUserEmail(createAccountRequest.getUserEmail());
		userAccountEntity.setUserName(createAccountRequest.getUserName());
		userAccountEntity.setPassword(createAccountRequest.getPassword());

		try {
			userAccountRepository.save(userAccountEntity);
		} catch (Exception e) {
			return utilMethods.errorResponse(failureResponseMessage);
		}

		try {

			String text = "Your account has been created \n" + "Username: " + userAccountEntity.getUserEmail()
					+ "\n Password " + userAccountEntity.getPassword();

			return  sendEmailUtil.sendEmail(userAccountEntity.getUserEmail(), forgotPasswordSendEmailFrom,
					"Bihar yoga account", text, successResponseMessage, failureResponseMessage);
			
			//return utilMethods.successResponse(successResponseMessage);


		} catch (MessagingException e) {
			return utilMethods.errorResponse("Not able to inform user by email! ");

		}

	}

}
