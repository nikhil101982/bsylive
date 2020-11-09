package com.yoga.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;

import com.yoga.api.entity.UserAccountEntity;
import com.yoga.api.model.LoginResponse;
import com.yoga.api.model.StatusMessageResponse;
import com.yoga.api.repository.UserAccountRepository;
import com.yoga.api.util.SendEmailUtil;
import com.yoga.api.util.UtilMethods;

public class SendEmail {
	
	@Autowired
	public JavaMailSender javaMailSender;
	public SendEmailUtil sendEmailUtil;
	@Value("${forgot.password.email.send.from}")
	public String forgotPasswordSendEmailFrom;
	public LoginResponse loginResponse;
	@Autowired
	public UserAccountRepository userAccountRepository;
	public UserAccountEntity userAccountEntity;
	public StatusMessageResponse statusMessageResponse;
	public String successResponseMessage;
	public String failureResponseMessage;
	public UtilMethods utilMethods;

	public SendEmail(SendEmailUtil sendEmailUtil, StatusMessageResponse statusMessageResponse,
			String successResponseMessage, String failureResponseMessage, UtilMethods utilMethods) {
		this.sendEmailUtil = sendEmailUtil;
		this.statusMessageResponse = statusMessageResponse;
		this.successResponseMessage = successResponseMessage;
		this.failureResponseMessage = failureResponseMessage;
		this.utilMethods = utilMethods;
	}
}